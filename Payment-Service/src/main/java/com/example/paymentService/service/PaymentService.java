package com.example.paymentService.service;

import java.util.List;
import java.util.UUID;

import com.example.paymentService.dto.PaymentRequestDTO;
import com.example.paymentService.dto.PaymentResponseDTO;
import com.example.paymentService.dto.RazorpayOrderResponseDTO;

public interface PaymentService {

    PaymentResponseDTO initiatePayment(PaymentRequestDTO request);

    PaymentResponseDTO processPayment(UUID paymentId, String transactionId, String status);

    List<PaymentResponseDTO> getPaymentByBooking(UUID bookingId);

    List<PaymentResponseDTO> getPaymentsByUser(Long userId);
    RazorpayOrderResponseDTO createRazorpayOrder(PaymentRequestDTO request);



    PaymentResponseDTO refundPayment(UUID paymentId);

    String getPaymentStatus(UUID paymentId);

    PaymentResponseDTO updatePaymentStatus(UUID paymentId, String status);

    String generateReceipt(UUID paymentId);

    Double getRevenue();
}