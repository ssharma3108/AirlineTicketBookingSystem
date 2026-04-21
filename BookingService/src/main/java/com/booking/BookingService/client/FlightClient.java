package com.booking.BookingService.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.UUID;

@FeignClient(name = "flight-service")
public interface FlightClient {

    @GetMapping("/flights/{flightId}")
    Object getFlightById(@PathVariable UUID flightId);

}