package com.booking.BookingService.service;





import com.booking.BookingService.dto.AddOnRequest;
import com.booking.BookingService.dto.BookingRequest;
import com.booking.BookingService.dto.BookingResponse;
import com.booking.BookingService.dto.FareResponse;
import com.booking.BookingService.entity.Booking;
import com.booking.BookingService.enums.BookingStatus;

import java.util.List;
import java.util.UUID;

public interface BookingService {

    BookingResponse createBooking(BookingRequest request);

    Booking getBookingById(UUID bookingId);

    Booking getBookingByPnr(String pnrCode);

    List<Booking> getBookingsByUser(UUID userId);

    List<Booking> getBookingsByFlight(UUID flightId);

    Booking cancelBooking(UUID bookingId);

    Booking updateStatus(UUID bookingId, BookingStatus status);

    FareResponse calculateFare(UUID flightId, Integer luggageKg);

    Booking addAddOn(UUID bookingId, AddOnRequest request);

    String generatePnr();

    List<Booking> getUpcomingBookings(UUID userId);
}