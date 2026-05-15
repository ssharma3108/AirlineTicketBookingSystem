package com.example.paymentService.client;

import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.paymentService.dto.BookingResponseDTO;

@FeignClient(name = "booking-service")
public interface BookingClient {

    @PostMapping("/bookings/payment/callback")
    BookingResponseDTO callback(@RequestParam UUID paymentId,
                               @RequestParam String transactionId,
                               @RequestParam String status);
}
