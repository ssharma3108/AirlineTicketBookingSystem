package com.paynment.PaynmentService.service.impl;

import com.paynment.PaynmentService.clients.BookingClient;
import com.paynment.PaynmentService.clients.UserClient;
import com.paynment.PaynmentService.dto.*;
import com.paynment.PaynmentService.entity.Payment;
import com.paynment.PaynmentService.enums.PaymentStatus;
import com.paynment.PaynmentService.exceptions.PaymentException;
import com.paynment.PaynmentService.exceptions.ResourceNotFoundException;
import com.paynment.PaynmentService.repository.PaymentRepository;

import com.paynment.PaynmentService.service.PaymentService;
import com.paynment.PaynmentService.util.PaymentUtils;
import com.paynment.PaynmentService.util.ReceiptGenerator;
import com.stripe.model.PaymentIntent;
import com.stripe.model.Refund;
import com.stripe.param.PaymentIntentCreateParams;
import com.stripe.param.RefundCreateParams;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private  PaymentRepository paymentRepository;

    private  BookingClient bookingClient;

    private  UserClient userClient;

    @Override
    public PaymentResponseDTO initiatePayment(PaymentRequestDTO request) {

        // 🔹 Validate amount
        PaymentUtils.validateAmount(request.getAmount());

        // 🔹 Validate booking
//        BookingClient.BookingResponse booking =
//                bookingClient.getBookingById(request.getBookingId());
//
//        if (booking == null || !"CONFIRMED".equalsIgnoreCase(booking.getStatus())) {
//            throw new PaymentException("Invalid or unconfirmed booking");
//        }

        // 🔹 Validate user
//        UserClient.UserResponse user =
//                userClient.getUserById(request.getUserId());
//
//        if (user == null) {
//            throw new ResourceNotFoundException("User not found");
//        }

        try {
            PaymentIntentCreateParams params =
                    PaymentIntentCreateParams.builder()
                            .setAmount(PaymentUtils.convertToSubUnit(request.getAmount()))
                            .setCurrency(request.getCurrency().toLowerCase())
                            .addPaymentMethodType("card")
                            .build();

            PaymentIntent intent = PaymentIntent.create(params);

            Payment payment = Payment.builder()
                    .bookingId(request.getBookingId())
                    .userId(request.getUserId())
                    .amount(request.getAmount())
                    .currency(request.getCurrency())
                    .paymentMode(request.getPaymentMode())
                    .status(PaymentStatus.PENDING)
                    .transactionId(intent.getId())
                    .gatewayResponse(intent.toJson())
                    .build();

            paymentRepository.save(payment);

            return PaymentResponseDTO.builder()
                    .paymentId(payment.getPaymentId())
                    .status(payment.getStatus())
                    .transactionId(intent.getId())
                    .clientSecret(intent.getClientSecret())
                    .build();

        } catch (Exception e) {
            throw new PaymentException("Stripe initiation failed: " + e.getMessage());
        }
    }

  // PROCESS PAYMENT
    @Override
    public PaymentResponseDTO processPayment(UUID paymentId) {

        try {
            Payment payment = paymentRepository.findById(paymentId)
                    .orElseThrow(() -> new ResourceNotFoundException("Payment not found"));

            PaymentIntent intent = PaymentIntent.retrieve(payment.getTransactionId());
            PaymentIntent confirmed = intent.confirm(
                    new HashMap<String, Object>() {{
                        put("payment_method", "pm_card_visa");
                    }}
            );

            if ("succeeded".equals(confirmed.getStatus())) {
                payment.setStatus(PaymentStatus.PAID);
                payment.setPaidAt(LocalDateTime.now());
            } else {
                payment.setStatus(PaymentStatus.FAILED);
            }

            payment.setGatewayResponse(confirmed.toJson());
            paymentRepository.save(payment);

            return mapToDTO(payment);

        } catch (Exception e) {
            throw new PaymentException("Stripe processing failed: " + e.getMessage());
        }
    }

    // GET BY BOOKING
    @Override
    public List<PaymentResponseDTO> getPaymentByBooking(Long bookingId) {
        return paymentRepository.findByBookingId(bookingId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // GET BY USER
    @Override
    public List<PaymentResponseDTO> getPaymentsByUser(Long userId) {
        return paymentRepository.findByUserId(userId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // REFUND
    @Override
    public PaymentResponseDTO refundPayment(RefundRequestDTO request) {

        try {
            Payment payment = paymentRepository.findById(request.getPaymentId())
                    .orElseThrow(() -> new ResourceNotFoundException("Payment not found"));

            if (payment.getStatus() != PaymentStatus.PAID) {
                throw new PaymentException("Only PAID payments can be refunded");
            }

            RefundCreateParams params =
                    RefundCreateParams.builder()
                            .setPaymentIntent(payment.getTransactionId())
                            .setAmount(PaymentUtils.convertToSubUnit(request.getRefundAmount()))
                            .build();

            Refund refund = Refund.create(params);

            payment.setStatus(PaymentStatus.REFUNDED);
            payment.setRefundAmount(request.getRefundAmount());
            payment.setRefundedAt(LocalDateTime.now());
            payment.setGatewayResponse(refund.toJson());

            paymentRepository.save(payment);

            return mapToDTO(payment);

        } catch (Exception e) {
            throw new PaymentException("Refund failed: " + e.getMessage());
        }
    }

    //  STATUS
    @Override
    public PaymentStatusResponseDTO getPaymentStatus(UUID paymentId) {

        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found"));

        return PaymentStatusResponseDTO.builder()
                .paymentId(paymentId)
                .status(payment.getStatus())
                .build();
    }

    //  UPDATE STATUS
    @Override
    public PaymentResponseDTO updatePaymentStatus(UUID paymentId, String status) {

        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found"));

        payment.setStatus(PaymentStatus.valueOf(status));
        paymentRepository.save(payment);

        return mapToDTO(payment);
    }

    //  RECEIPT
    @Override
    public ReceiptDTO generateReceipt(UUID paymentId) {

        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found"));

        return ReceiptGenerator.generateReceipt(payment);
    }

    // REVENUE
    @Override
    public Double getRevenue() {
        return paymentRepository.findAll()
                .stream()
                .filter(p -> p.getStatus() == PaymentStatus.PAID)
                .mapToDouble(Payment::getAmount)
                .sum();
    }

    // 🔹 Mapper
    private PaymentResponseDTO mapToDTO(Payment payment) {
        return PaymentResponseDTO.builder()
                .paymentId(payment.getPaymentId())
                .status(payment.getStatus())
                .transactionId(payment.getTransactionId())
                .paidAt(payment.getPaidAt())
                .build();
    }
}