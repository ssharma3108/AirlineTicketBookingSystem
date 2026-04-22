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




    // ✅ 1. Initiate Payment (Stripe PaymentIntent creation)
    @PostMapping("/initiate")
    public PaymentResponseDTO initiatePayment(@RequestBody PaymentRequestDTO request) {
        return paymentService.initiatePayment(request);
    }

    // ✅ 2. Process Payment (Confirm Stripe payment)
    @PostMapping("/process/{paymentId}")
    public PaymentResponseDTO processPayment(@PathVariable UUID paymentId) {
        return paymentService.processPayment(paymentId);
    }

    // ✅ 3. Refund Payment
    @PostMapping("/refund")
    public PaymentResponseDTO refundPayment(@RequestBody RefundRequestDTO request) {
        return paymentService.refundPayment(request);
    }

    // ✅ 4. Get Payments by Booking
    @GetMapping("/booking/{bookingId}")
    public List<PaymentResponseDTO> getPaymentsByBooking(@PathVariable Long bookingId) {
        return paymentService.getPaymentByBooking(bookingId);
    }

    // ✅ 5. Get Payments by User
    @GetMapping("/user/{userId}")
    public List<PaymentResponseDTO> getPaymentsByUser(@PathVariable Long userId) {
        return paymentService.getPaymentsByUser(userId);
    }

    // ✅ 6. Get Payment Status
    @GetMapping("/status/{paymentId}")
    public PaymentStatusResponseDTO getPaymentStatus(@PathVariable UUID paymentId) {
        return paymentService.getPaymentStatus(paymentId);
    }

    // ✅ 7. Generate Receipt
    @GetMapping("/receipt/{paymentId}")
    public ReceiptDTO generateReceipt(@PathVariable UUID paymentId) {
        return paymentService.generateReceipt(paymentId);
    }

    // ✅ 8. Get Total Revenue
    @GetMapping("/revenue")
    public Double getRevenue() {
        return paymentService.getRevenue();
    }
}