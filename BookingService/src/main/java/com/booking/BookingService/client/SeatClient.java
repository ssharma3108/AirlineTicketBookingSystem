package com.booking.BookingService.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "seat-service")
public interface SeatClient {

    @PutMapping("/seats/book/{flightId}")
    Boolean reserveSeat(@PathVariable UUID flightId);

}