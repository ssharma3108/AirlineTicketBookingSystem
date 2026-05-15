package com.example.bookingService.client;

import java.util.List;
import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.bookingService.dto.PaymentRequestDTO;
import com.example.bookingService.dto.PaymentResponseDTO;

@FeignClient(name = "payment-service",url = "http://localhost:8086")
public interface PaymentClient {

    @PostMapping("/payments/initiate")
    PaymentResponseDTO initiate(@RequestBody PaymentRequestDTO request);

    @PostMapping("/payments/process")
    PaymentResponseDTO process(
            @RequestParam UUID paymentId,
            @RequestParam String transactionId,
            @RequestParam String status
    );

    @GetMapping("/payments/booking/{bookingId}")
    List<PaymentResponseDTO> getByBooking(@PathVariable UUID bookingId);

    @PostMapping("/payments/refund/{paymentId}")
    PaymentResponseDTO refund(@PathVariable UUID paymentId);
}
