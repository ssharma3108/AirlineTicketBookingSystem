package com.admin.Admin_Service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "BOOKING-SERVICE")
public interface BookingClient {

    @GetMapping("/api/bookings")
    List<Object> getAllBookings();

    @GetMapping("/api/bookings/count")
    Long getBookingCount();

}