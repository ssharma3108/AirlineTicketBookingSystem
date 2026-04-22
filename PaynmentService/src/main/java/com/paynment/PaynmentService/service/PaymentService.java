package com.paynment.PaynmentService.service;



import com.paynment.PaynmentService.dto.*;

import java.util.List;
import java.util.UUID;

public interface PaymentService {

    PaymentResponseDTO initiatePayment(PaymentRequestDTO request);

    PaymentResponseDTO processPayment(UUID paymentId);

    List<PaymentResponseDTO> getPaymentByBooking(Long bookingId);

    List<PaymentResponseDTO> getPaymentsByUser(Long userId);

    PaymentResponseDTO refundPayment(RefundRequestDTO request);

    PaymentStatusResponseDTO getPaymentStatus(UUID paymentId);

    PaymentResponseDTO updatePaymentStatus(UUID paymentId, String status);

    ReceiptDTO generateReceipt(UUID paymentId);

    Double getRevenue();
}