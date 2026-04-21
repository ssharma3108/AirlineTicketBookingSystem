package com.booking.BookingService.service.impl;


import com.booking.BookingService.dto.AddOnRequest;
import com.booking.BookingService.dto.BookingRequest;
import com.booking.BookingService.dto.BookingResponse;
import com.booking.BookingService.dto.FareResponse;
import com.booking.BookingService.entity.Booking;
import com.booking.BookingService.enums.BookingStatus;
import com.booking.BookingService.repository.BookingRepository;
import com.booking.BookingService.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;

    @Override
    public BookingResponse createBooking(BookingRequest request) {

        FareResponse fare = calculateFare(
                request.getFlightId(),
                request.getLuggageKg()
        );

        Booking booking = new Booking();

        booking.setUserId(request.getUserId());
        booking.setFlightId(request.getFlightId());
        booking.setTripType(request.getTripType());

        booking.setMealPreference(request.getMealPreference());
        booking.setLuggageKg(request.getLuggageKg());

        booking.setContactEmail(request.getContactEmail());
        booking.setContactPhone(request.getContactPhone());

        booking.setPnrCode(generatePnr());
        booking.setStatus(BookingStatus.CONFIRMED);

        booking.setBaseFare(fare.getBaseFare());
        booking.setTaxes(fare.getTaxes());
        booking.setTotalFare(fare.getTotalFare());

        booking.setBookedAt(LocalDateTime.now());

        Booking saved = bookingRepository.save(booking);

        return new BookingResponse(
                saved.getBookingId().toString(),
                saved.getPnrCode(),
                saved.getStatus(),
                saved.getTotalFare(),
                "Booking created successfully"
        );
    }

    @Override
    public Booking getBookingById(UUID bookingId) {
        return bookingRepository.findById(bookingId).orElseThrow();
    }

    @Override
    public Booking getBookingByPnr(String pnrCode) {
        return bookingRepository.findByPnrCode(pnrCode).orElseThrow();
    }

    @Override
    public List<Booking> getBookingsByUser(UUID userId) {
        return bookingRepository.findByUserId(userId);
    }

    @Override
    public List<Booking> getBookingsByFlight(UUID flightId) {
        return bookingRepository.findByFlightId(flightId);
    }

    @Override
    public Booking cancelBooking(UUID bookingId) {

        Booking booking = getBookingById(bookingId);

        booking.setStatus(BookingStatus.CANCELLED);

        return bookingRepository.save(booking);
    }

    @Override
    public Booking updateStatus(UUID bookingId, BookingStatus status) {

        Booking booking = getBookingById(bookingId);

        booking.setStatus(status);

        return bookingRepository.save(booking);
    }

    @Override
    public FareResponse calculateFare(UUID flightId, Integer luggageKg) {

        double baseFare = 5000;
        double taxes = 500;
        double luggageCharge = luggageKg * 100.0;

        double total = baseFare + taxes + luggageCharge;

        return new FareResponse(
                baseFare,
                taxes,
                luggageCharge,
                total
        );
    }

    @Override
    public Booking addAddOn(UUID bookingId, AddOnRequest request) {

        Booking booking = getBookingById(bookingId);

        booking.setMealPreference(request.getMealPreference());
        booking.setLuggageKg(request.getLuggageKg());

        return bookingRepository.save(booking);
    }

    @Override
    public String generatePnr() {

        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

        StringBuilder sb = new StringBuilder();

        Random random = new Random();

        for (int i = 0; i < 6; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }

        return sb.toString();
    }

    @Override
    public List<Booking> getUpcomingBookings(UUID userId) {

        return bookingRepository.findByUserIdAndStatus(
                userId,
                BookingStatus.CONFIRMED
        );
    }
}