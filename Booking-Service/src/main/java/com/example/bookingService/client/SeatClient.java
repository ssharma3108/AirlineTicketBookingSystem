package com.example.bookingService.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "seat-service",url = "http://localhost:8083")
public interface SeatClient {

    @PutMapping("/seats/hold")
    String holdSeat(
            @RequestParam Long flightId,
            @RequestParam String seatNumber,
            @RequestHeader("X-User-Id") String userId
    );

    @PutMapping("/seats/release")
    String releaseSeat(
            @RequestParam Long flightId,
            @RequestParam String seatNumber
    );

    @PostMapping("/seats/lock")
    void lockSeat(@RequestParam Long flightId,
                  @RequestParam String seatNumber);
}