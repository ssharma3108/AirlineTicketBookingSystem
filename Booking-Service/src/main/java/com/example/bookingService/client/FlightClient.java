package com.example.bookingService.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import com.example.bookingService.dto.FlightResponseDTO;

@FeignClient(name = "flight-service", url = "http://localhost:8082")
public interface FlightClient {

    @GetMapping("/flights/{id}")
    FlightResponseDTO getFlightById(@PathVariable("id") Long id);

    @PutMapping("/flights/{id}/decrement")
    void decrementSeat(@PathVariable("id") Long id, @RequestParam("count") int count);

    @PutMapping("/flights/{id}/increment")
    void incrementSeat(@PathVariable("id") Long id, @RequestParam("count") int count);

}
