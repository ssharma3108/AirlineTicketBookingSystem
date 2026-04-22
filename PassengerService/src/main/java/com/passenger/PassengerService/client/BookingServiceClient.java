package com.passenger.PassengerService.client;

import com.passenger.PassengerService.dto.BookingResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "booking-service")
public interface BookingServiceClient {

    @GetMapping("/bookings/{id}")
    BookingResponse getBookingById(@PathVariable("id") Long id);

}