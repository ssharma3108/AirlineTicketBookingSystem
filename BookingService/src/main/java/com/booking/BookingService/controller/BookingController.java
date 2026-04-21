package com.booking.BookingService.controller;

import com.booking.BookingService.dto.AddOnRequest;
import com.booking.BookingService.dto.BookingRequest;
import com.booking.BookingService.dto.BookingResponse;
import com.booking.BookingService.dto.FareResponse;
import com.booking.BookingService.entity.Booking;
import com.booking.BookingService.enums.BookingStatus;
import com.booking.BookingService.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
public class BookingController {

    @Autowired
    private  BookingService BookingService;

    // ======================================
    // CREATE BOOKING
    // POST /bookings
    // ======================================
    @PostMapping
    public BookingResponse createBooking(
            @RequestBody BookingRequest request) {

        return BookingService.createBooking(request);
    }

    // ======================================
    // GET BOOKING BY ID
    // GET /bookings/{id}
    // ======================================
    @GetMapping("/{id}")
    public Booking getBookingById(
            @PathVariable UUID id) {

        return BookingService.getBookingById(id);
    }

    // ======================================
    // GET BOOKING BY PNR
    // GET /bookings/pnr/{pnr}
    // ======================================
    @GetMapping("/pnr/{pnr}")
    public Booking getBookingByPnr(
            @PathVariable String pnr) {

        return BookingService.getBookingByPnr(pnr);
    }

    // ======================================
    // GET BOOKINGS BY USER
    // GET /bookings/user/{userId}
    // ======================================
    @GetMapping("/user/{userId}")
    public List<Booking> getBookingsByUser(
            @PathVariable UUID userId) {

        return BookingService.getBookingsByUser(userId);
    }

    // ======================================
    // GET BOOKINGS BY FLIGHT
    // GET /bookings/flight/{flightId}
    // ======================================
    @GetMapping("/flight/{flightId}")
    public List<Booking> getBookingsByFlight(
            @PathVariable UUID flightId) {

        return BookingService.getBookingsByFlight(flightId);
    }

    // ======================================
    // CANCEL BOOKING
    // PUT /bookings/cancel/{id}
    // ======================================
    @PutMapping("/cancel/{id}")
    public Booking cancelBooking(
            @PathVariable UUID id) {

        return BookingService.cancelBooking(id);
    }

    // ======================================
    // UPDATE STATUS
    // PUT /bookings/status/{id}?status=CONFIRMED
    // ======================================
    @PutMapping("/status/{id}")
    public Booking updateStatus(
            @PathVariable UUID id,
            @RequestParam BookingStatus status) {

        return BookingService.updateStatus(id, status);
    }

    // ======================================
    // CALCULATE FARE
    // GET /bookings/fare/{flightId}/{kg}
    // ======================================
    @GetMapping("/fare/{flightId}/{kg}")
    public FareResponse calculateFare(
            @PathVariable UUID flightId,
            @PathVariable Integer kg) {

        return BookingService.calculateFare(flightId, kg);
    }

    // ======================================
    // ADD ADDON
    // POST /bookings/addon/{id}
    // ======================================
    @PostMapping("/addon/{id}")
    public Booking addAddOn(
            @PathVariable UUID id,
            @RequestBody AddOnRequest request) {

        return BookingService.addAddOn(id, request);
    }

    // ======================================
    // UPCOMING BOOKINGS
    // GET /bookings/upcoming/{userId}
    // ======================================
    @GetMapping("/upcoming/{userId}")
    public List<Booking> upcomingBookings(
            @PathVariable UUID userId) {

        return BookingService.getUpcomingBookings(userId);
    }
}