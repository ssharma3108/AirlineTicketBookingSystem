package com.paynment.PaynmentService.controller;


import com.paynment.PaynmentService.dto.*;
import com.paynment.PaynmentService.service.PaymentService;
import com.paynment.PaynmentService.util.PaymentUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentResource {

    @Autowired
    private PaymentService paymentService;





    @PostMapping("/initiate")
    public PaymentResponseDTO initiatePayment(@RequestBody PaymentRequestDTO request) {
        return paymentService.initiatePayment(request);
    }

    @PostMapping("/process/{paymentId}")
    public PaymentResponseDTO processPayment(@PathVariable UUID paymentId) {
        return paymentService.processPayment(paymentId);
    }

    @PostMapping("/refund")
    public PaymentResponseDTO refundPayment(@RequestBody RefundRequestDTO request) {
        return paymentService.refundPayment(request);
    }

    @GetMapping("/booking/{bookingId}")
    public List<PaymentResponseDTO> getPaymentsByBooking(@PathVariable Long bookingId) {
        return paymentService.getPaymentByBooking(bookingId);
    }

    @GetMapping("/user/{userId}")
    public List<PaymentResponseDTO> getPaymentsByUser(@PathVariable Long userId) {
        return paymentService.getPaymentsByUser(userId);
    }


    @GetMapping("/status/{paymentId}")
    public PaymentStatusResponseDTO getPaymentStatus(@PathVariable UUID paymentId) {
        return paymentService.getPaymentStatus(paymentId);
    }


    @GetMapping("/receipt/{paymentId}")
    public ReceiptDTO generateReceipt(@PathVariable UUID paymentId) {
        return paymentService.generateReceipt(paymentId);
    }


    @GetMapping("/revenue")
    public Double getRevenue() {
        return paymentService.getRevenue();
    }
}