package com.example.bookingService.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bookingService.client.FlightClient;
import com.example.bookingService.client.PaymentClient;
import com.example.bookingService.client.SeatClient;
import com.example.bookingService.dto.*;
import com.example.bookingService.entity.*;
import com.example.bookingService.repository.BookingRepository;

@Service
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final PaymentClient paymentClient;

    @Autowired
    private SeatClient seatClient;

    @Autowired
    private FlightClient flightClient;

    public BookingServiceImpl(BookingRepository bookingRepository, PaymentClient paymentClient) {
        this.bookingRepository = bookingRepository;
        this.paymentClient = paymentClient;
    }

    @Override
    public BookingResponseDTO createBooking(BookingRequestDTO request) {
        if (request.getUserId() == null || request.getUserId() <= 0) throw new RuntimeException("Valid user id is required");
        if (request.getFlightId() == null || request.getFlightId() <= 0) throw new RuntimeException("Valid flight id is required");

        if (request.getTripType() == null || request.getTripType().isBlank()) request.setTripType("ONE_WAY");
        if (request.getMealPreference() == null || request.getMealPreference().isBlank()) request.setMealPreference("NO_MEAL");
        if (request.getSeatClass() == null || request.getSeatClass().isBlank()) request.setSeatClass("NORMAL");
        if (request.getLuggageKg() == null || request.getLuggageKg() < 0) request.setLuggageKg(0);

        if (request.getSeatNumber() == null || request.getSeatNumber().isBlank()) {
            request.setSeatNumber(request.getSeatClass().substring(0, 1).toUpperCase() + request.getFlightId());
        }

        FareSummaryDTO fare = calculateFare(
                request.getFlightId(),
                request.getLuggageKg(),
                request.getTripType(),
                request.getMealPreference(),
                request.getSeatClass()
        );

        boolean seatHeld = false;
        try {
            seatClient.holdSeat(request.getFlightId(), request.getSeatNumber(), String.valueOf(request.getUserId()));
            seatHeld = true;
        } catch (Exception e) {
            String msg = e.getMessage() == null ? "" : e.getMessage().toLowerCase();
            if (msg.contains("not available") || msg.contains("already")) {
                throw new RuntimeException("Selected seat is already booked. Please choose another seat.");
            }
            System.out.println("Seat hold skipped: " + e.getMessage());
        }

        try {
            flightClient.decrementSeat(request.getFlightId(), 1);
        } catch (Exception e) {
            try {
                if (seatHeld) seatClient.releaseSeat(request.getFlightId(), request.getSeatNumber());
            } catch (Exception ignored) {}

            String msg = e.getMessage() == null ? "" : e.getMessage().toLowerCase();
            if (msg.contains("no seats") || msg.contains("sold out") || msg.contains("insufficient")) {
                throw new RuntimeException("No seats available for this flight.");
            }
            throw new RuntimeException("Flight seat update failed. " + e.getMessage());

        }

        try {
            Booking booking = new Booking();
            booking.setUserId(request.getUserId());
            booking.setFlightId(request.getFlightId());
            booking.setSeatNumber(request.getSeatNumber());
            booking.setPnrCode(generatePnr());
            booking.setTripType(parseTripType(request.getTripType()));
            booking.setStatus(BookingStatus.PENDING);
            booking.setRefundStatus(RefundStatus.NON_REFUNDED);
            booking.setBaseFare(fare.getBaseFare());
            booking.setTaxes(fare.getTaxes());
            booking.setTotalFare(fare.getTotalFare());
            booking.setMealPreference(request.getMealPreference());
            booking.setLuggageKg(request.getLuggageKg());
            booking.setContactEmail(request.getContactEmail());
            booking.setContactPhone(request.getContactPhone());
            booking.setBookedAt(LocalDateTime.now());

            return mapToResponse(bookingRepository.save(booking));
        } catch (Exception e) {
            try {
                if (seatHeld) seatClient.releaseSeat(request.getFlightId(), request.getSeatNumber());
                flightClient.incrementSeat(request.getFlightId(), 1);
            } catch (Exception rollbackError) {
                System.out.println("Rollback failed: " + rollbackError.getMessage());
            }
            throw new RuntimeException("Booking failed: " + e.getMessage());
        }
    }

    @Override public BookingResponseDTO getBookingById(UUID bookingId) { return mapToResponse(bookingRepository.findById(bookingId).orElseThrow(() -> new RuntimeException("Booking not found"))); }
    @Override public BookingResponseDTO getBookingByPnr(String pnr) { return mapToResponse(bookingRepository.findByPnrCode(pnr).orElseThrow(() -> new RuntimeException("PNR not found"))); }
    @Override public List<BookingResponseDTO> getAllBookings() { return bookingRepository.findAll().stream().map(this::mapToResponse).collect(Collectors.toList()); }
    @Override public List<BookingResponseDTO> getBookingsByUser(Long userId) { return bookingRepository.findByUserId(userId).stream().map(this::mapToResponse).collect(Collectors.toList()); }
    @Override public List<BookingResponseDTO> getBookingsByFlight(Long flightId) { return bookingRepository.findByFlightId(flightId).stream().map(this::mapToResponse).collect(Collectors.toList()); }

    @Override
    public BookingResponseDTO cancelBooking(UUID bookingId) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new RuntimeException("Booking not found"));
        booking.setStatus(BookingStatus.CANCELLED);
        try { seatClient.releaseSeat(booking.getFlightId(), booking.getSeatNumber()); } catch (Exception e) { System.out.println("Seat release skipped: " + e.getMessage()); }
        try { flightClient.incrementSeat(booking.getFlightId(), 1); } catch (Exception e) { System.out.println("Flight seat increment skipped: " + e.getMessage()); }
        return mapToResponse(bookingRepository.save(booking));
    }

    @Override
    public BookingResponseDTO requestRefund(UUID bookingId) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new RuntimeException("Booking not found"));
        if (booking.getStatus() != BookingStatus.CONFIRMED) {
            try {
                List<PaymentResponseDTO> payments = paymentClient.getByBooking(bookingId);
                boolean paid = payments.stream().anyMatch(p -> "PAID".equalsIgnoreCase(p.getStatus()));
                if (paid) booking.setStatus(BookingStatus.CONFIRMED);
                else throw new RuntimeException("Refund can be requested only for confirmed bookings");
            } catch (RuntimeException e) { throw e; }
            catch (Exception e) { throw new RuntimeException("Refund can be requested only for confirmed bookings"); }
        }
        if (booking.getRefundStatus() == RefundStatus.REFUNDED) throw new RuntimeException("Booking already refunded");
        booking.setRefundStatus(RefundStatus.REQUESTED);
        return mapToResponse(bookingRepository.save(booking));
    }

    @Override
    public BookingResponseDTO approveRefund(UUID bookingId) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new RuntimeException("Booking not found"));
        if (booking.getRefundStatus() != RefundStatus.REQUESTED) throw new RuntimeException("No pending refund request for this booking");

        List<PaymentResponseDTO> payments = paymentClient.getByBooking(bookingId);
        PaymentResponseDTO paidPayment = payments.stream()
                .filter(payment -> "PAID".equalsIgnoreCase(payment.getStatus()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No paid payment found for this booking"));

        paymentClient.refund(paidPayment.getPaymentId());

        booking.setStatus(BookingStatus.CANCELLED);
        booking.setRefundStatus(RefundStatus.REFUNDED);
        try { seatClient.releaseSeat(booking.getFlightId(), booking.getSeatNumber()); } catch (Exception e) { System.out.println("Seat release skipped: " + e.getMessage()); }
        try { flightClient.incrementSeat(booking.getFlightId(), 1); } catch (Exception e) { System.out.println("Flight seat increment skipped: " + e.getMessage()); }

        return mapToResponse(bookingRepository.save(booking));
    }

    @Override
    public BookingResponseDTO rejectRefund(UUID bookingId) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new RuntimeException("Booking not found"));
        if (booking.getRefundStatus() != RefundStatus.REQUESTED) throw new RuntimeException("No pending refund request for this booking");
        booking.setStatus(BookingStatus.CONFIRMED);
        booking.setRefundStatus(RefundStatus.REJECTED);
        return mapToResponse(bookingRepository.save(booking));
    }

    @Override public List<BookingResponseDTO> getRefundRequests() { return bookingRepository.findAll().stream().filter(b -> b.getRefundStatus() == RefundStatus.REQUESTED).map(this::mapToResponse).collect(Collectors.toList()); }
    @Override public BookingResponseDTO updateBookingStatus(UUID bookingId, String status) { Booking b = bookingRepository.findById(bookingId).orElseThrow(() -> new RuntimeException("Booking not found")); b.setStatus(BookingStatus.valueOf(status.trim().toUpperCase())); return mapToResponse(bookingRepository.save(b)); }
    @Override public void deleteBooking(UUID bookingId) { Booking b = bookingRepository.findById(bookingId).orElseThrow(() -> new RuntimeException("Booking not found")); bookingRepository.delete(b); }

    @Override
    public FareSummaryDTO calculateFare(Long flightId, Integer luggageKg, String tripType, String mealPreference, String seatClass) {
        double flightBase = 5000.0;
        try {
            FlightResponseDTO flight = flightClient.getFlightById(flightId);
            if (flight.getBaseFare() != null) flightBase = flight.getBaseFare();
        } catch (Exception e) {
            System.out.println("Could not load flight fare. Using default fare.");
        }

        TripType parsedTripType = parseTripType(tripType);
        double tripMultiplier = parsedTripType == TripType.ROUND_TRIP ? 2.0 : 1.0;
        String meal = mealPreference == null ? "NO_MEAL" : mealPreference.toUpperCase();
        double mealCharge = "VEG".equals(meal) ? 100.0 : ("NON_VEG".equals(meal) ? 250.0 : 0.0);
        String seat = seatClass == null ? "NORMAL" : seatClass.toUpperCase();
        double seatCharge = "ECONOMY".equals(seat) ? 500.0 : (("BUSINESS".equals(seat) || "FIRST".equals(seat)) ? 1500.0 : 0.0);

        int luggage = luggageKg != null ? Math.max(luggageKg, 0) : 0;
        double luggageCharge = Math.max(luggage - 15, 0) * 75.0;
        double baseFare = (flightBase * tripMultiplier) + mealCharge + seatCharge + luggageCharge;

        FareSummaryDTO dto = new FareSummaryDTO();
        dto.setBaseFare(baseFare);
        dto.setTaxes(0.0);
        dto.setTotalFare(baseFare);
        return dto;
    }

    @Override
    public BookingResponseDTO addAddOn(UUID bookingId, String meal, Integer luggageKg) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new RuntimeException("Booking not found"));
        booking.setMealPreference(meal);
        booking.setLuggageKg(luggageKg);
        return mapToResponse(bookingRepository.save(booking));
    }

    @Override
    public List<BookingResponseDTO> getUpcomingBookings(Long userId) {
        return bookingRepository.findByUserId(userId).stream()
                .filter(booking -> booking.getStatus() == BookingStatus.CONFIRMED)
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public PaymentResponseDTO startPayment(UUID bookingId, String method) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new RuntimeException("Booking not found"));
        if (booking.getStatus() != BookingStatus.PENDING) throw new RuntimeException("Booking not in valid state");

        PaymentRequestDTO request = new PaymentRequestDTO();
        request.setBookingId(bookingId);
        request.setUserId(booking.getUserId());
        request.setAmount(booking.getTotalFare());
        request.setCurrency("INR");

        try { request.setPaymentMode(PaymentMode.valueOf(method.toUpperCase())); }
        catch (Exception e) { throw new RuntimeException("Invalid payment method"); }

        return paymentClient.initiate(request);
    }

    @Override
    public BookingResponseDTO completePayment(UUID paymentId, String transactionId, String status) {
        PaymentResponseDTO paymentRes = paymentClient.process(paymentId, transactionId, status);
        Booking booking = bookingRepository.findById(paymentRes.getBookingId()).orElseThrow(() -> new RuntimeException("Booking not found"));

        if ("PAID".equalsIgnoreCase(paymentRes.getStatus())) {
            booking.setStatus(BookingStatus.CONFIRMED);
            try { seatClient.lockSeat(booking.getFlightId(), booking.getSeatNumber()); } catch (Exception e) { System.out.println("Seat lock skipped: " + e.getMessage()); }
        } else {
            booking.setStatus(BookingStatus.CANCELLED);
            try { seatClient.releaseSeat(booking.getFlightId(), booking.getSeatNumber()); } catch (Exception e) { System.out.println("Seat release skipped: " + e.getMessage()); }
            try { flightClient.incrementSeat(booking.getFlightId(), 1); } catch (Exception e) { System.out.println("Flight seat increment skipped: " + e.getMessage()); }
        }

        return mapToResponse(bookingRepository.save(booking));
    }

    private String generatePnr() { return UUID.randomUUID().toString().replace("-", "").substring(0, 6).toUpperCase(); }

    private TripType parseTripType(String value) {
        if (value == null || value.isBlank()) return TripType.ONE_WAY;
        String normalized = value.trim().toUpperCase().replace(" ", "_").replace("-", "_");
        if ("ROUND".equals(normalized) || "TWO_WAY".equals(normalized) || "RETURN".equals(normalized)) return TripType.ROUND_TRIP;
        if ("ONEWAY".equals(normalized)) return TripType.ONE_WAY;
        return TripType.valueOf(normalized);
    }

    private BookingResponseDTO mapToResponse(Booking booking) {
        BookingResponseDTO dto = new BookingResponseDTO();
        dto.setBookingId(booking.getBookingId());
        dto.setPnrCode(booking.getPnrCode());
        dto.setUserId(booking.getUserId());
        dto.setFlightId(booking.getFlightId());
        dto.setStatus(booking.getStatus().name());
        dto.setRefundStatus(booking.getRefundStatus() == null ? RefundStatus.NON_REFUNDED.name() : booking.getRefundStatus().name());
        dto.setTotalFare(booking.getTotalFare());
        dto.setSeatNumber(booking.getSeatNumber());
        return dto;
    }
}
