package com.example.paymentService.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.*;

import com.example.paymentService.dto.PaymentRequestDTO;
import com.example.paymentService.dto.PaymentResponseDTO;
import com.example.paymentService.dto.RazorpayOrderResponseDTO;
import com.example.paymentService.service.PaymentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService service;

    public PaymentController(PaymentService service) {
        this.service = service;
    }

    @PostMapping("/initiate")
    public PaymentResponseDTO initiate(@Valid @RequestBody PaymentRequestDTO request) {
        return service.initiatePayment(request);
    }

    // ✅ Required for Razorpay checkout
    @PostMapping("/razorpay/order")
    public RazorpayOrderResponseDTO createRazorpayOrder(@Valid @RequestBody PaymentRequestDTO request) {
        return service.createRazorpayOrder(request);
    }

    @PostMapping("/process")
    public PaymentResponseDTO process(@RequestParam UUID paymentId,
                                      @RequestParam String transactionId,
                                      @RequestParam String status) {
        return service.processPayment(paymentId, transactionId, status);
    }

    @PostMapping("/refund/{paymentId}")
    public PaymentResponseDTO refund(@PathVariable UUID paymentId) {
        return service.refundPayment(paymentId);
    }

    @GetMapping("/booking/{bookingId}")
    public List<PaymentResponseDTO> getByBooking(@PathVariable UUID bookingId) {
        return service.getPaymentByBooking(bookingId);
    }

    @GetMapping("/user/{userId}")
    public List<PaymentResponseDTO> getByUser(@PathVariable Long userId) {
        return service.getPaymentsByUser(userId);
    }

    @GetMapping("/status/{paymentId}")
    public String getStatus(@PathVariable UUID paymentId) {
        return service.getPaymentStatus(paymentId);
    }

    @GetMapping("/receipt/{paymentId}")
    public String receipt(@PathVariable UUID paymentId) {
        return service.generateReceipt(paymentId);
    }

    @GetMapping("/revenue")
    public Double revenue() {
        return service.getRevenue();
    }
}
