package com.passenger.PassengerService.client;

import com.passenger.PassengerService.dto.SeatRequest;
import com.passenger.PassengerService.dto.SeatResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "seat-service")
public interface SeatServiceClient {

    @PutMapping("/seats/assign")
    SeatResponse assignSeat(@RequestBody SeatRequest request);

}