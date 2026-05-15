package com.example.bookingService.service;

import java.util.List;
import java.util.UUID;

import com.example.bookingService.dto.BookingRequestDTO;
import com.example.bookingService.dto.BookingResponseDTO;
import com.example.bookingService.dto.FareSummaryDTO;
import com.example.bookingService.dto.PaymentResponseDTO;

public interface BookingService {

    BookingResponseDTO createBooking(BookingRequestDTO request);

    BookingResponseDTO getBookingById(UUID bookingId);

    BookingResponseDTO getBookingByPnr(String pnr);

    List<BookingResponseDTO> getAllBookings();

    List<BookingResponseDTO> getBookingsByUser(Long userId);

    List<BookingResponseDTO> getBookingsByFlight(Long flightId);

    BookingResponseDTO cancelBooking(UUID bookingId);

    BookingResponseDTO requestRefund(UUID bookingId);

    BookingResponseDTO approveRefund(UUID bookingId);

    BookingResponseDTO rejectRefund(UUID bookingId);

    List<BookingResponseDTO> getRefundRequests();

    BookingResponseDTO updateBookingStatus(UUID bookingId, String status);

    void deleteBooking(UUID bookingId);

    FareSummaryDTO calculateFare(
            Long flightId,
            Integer luggageKg,
            String tripType,
            String mealPreference,
            String seatClass
    );

    BookingResponseDTO addAddOn(UUID bookingId, String meal, Integer luggageKg);

    List<BookingResponseDTO> getUpcomingBookings(Long userId);

    PaymentResponseDTO startPayment(UUID bookingId, String method);

    // IMPORTANT: first param is paymentId
    BookingResponseDTO completePayment(UUID paymentId, String transactionId, String status);
}
