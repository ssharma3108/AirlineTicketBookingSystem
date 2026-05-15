package com.example.passengerService.client;

import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.passengerService.dto.BookingDTO;

@FeignClient(name = "booking-service")
public interface BookingClient {

    // Get booking by ID
    @GetMapping("/bookings/{bookingId}")
    BookingDTO getBooking(@PathVariable("bookingId") UUID bookingId);

}