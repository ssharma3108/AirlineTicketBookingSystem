package com.example.paymentService.service;

import java.time.LocalDateTime;
import java.util.*;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.example.paymentService.client.BookingClient;
import com.example.paymentService.dto.*;
import com.example.paymentService.entity.*;
import com.example.paymentService.repository.PaymentRepository;

@Service
public class PaymentServiceImpl implements PaymentService {

    private PaymentRepository repository;
    private BookingClient bookingClient;

    @Value("${razorpay.key.id}")
    private String razorpayKeyId;

    @Value("${razorpay.key.secret}")
    private String razorpayKeySecret;

    public PaymentServiceImpl(PaymentRepository repository,BookingClient bookingClient) {
        this.repository = repository;
        this.bookingClient =bookingClient;
    }

    //This method does NOT contact Razorpay.
    //It only creates local payment entry.
    @Override
    public PaymentResponseDTO initiatePayment(PaymentRequestDTO request) {

        Payment payment = new Payment();

        payment.setBookingId(request.getBookingId());

        payment.setBookingId(request.getBookingId());
        payment.setUserId(request.getUserId());
        payment.setAmount(request.getAmount());
        payment.setCurrency(request.getCurrency());

        payment.setPaymentMode(
                PaymentMode.valueOf(request.getPaymentMode().toUpperCase())
        );

        payment.setStatus(PaymentStatus.PENDING);

        repository.save(payment);

        return mapToDTO(payment);
    }


    //PAYMENT GATEWAY METHOD.
    @Override
    public RazorpayOrderResponseDTO createRazorpayOrder(PaymentRequestDTO request) {
        try {
            if (razorpayKeyId == null
                    || razorpayKeySecret == null
                    || razorpayKeyId.isBlank()
                    || razorpayKeySecret.isBlank()
                    || razorpayKeyId.startsWith("YOUR_")
                    || razorpayKeySecret.startsWith("YOUR_")) {
                throw new RuntimeException("Add real Razorpay key id and secret in Payment-Service application.properties");
            }

            if (request.getCurrency() == null || request.getCurrency().isBlank()) {
                request.setCurrency("INR");
            }

            if (request.getPaymentMode() == null || request.getPaymentMode().isBlank()) {
                request.setPaymentMode("UPI");
            }

            Payment payment = new Payment();
            payment.setBookingId(request.getBookingId());
            payment.setUserId(request.getUserId());
            payment.setAmount(request.getAmount());
            payment.setCurrency(request.getCurrency());
            payment.setPaymentMode(PaymentMode.valueOf(request.getPaymentMode().toUpperCase()));
            payment.setStatus(PaymentStatus.PENDING);

            Payment saved = repository.save(payment);

            RazorpayClient razorpay = new RazorpayClient(razorpayKeyId, razorpayKeySecret);
            int amountInPaise = (int) Math.round(request.getAmount() * 100);

            JSONObject options = new JSONObject();
            options.put("amount", amountInPaise);
            options.put("currency", request.getCurrency());
            options.put("receipt", saved.getPaymentId().toString());

            Order order = razorpay.orders.create(options);
            saved.setGatewayResponse(order.toString());
            repository.save(saved);

            RazorpayOrderResponseDTO response = new RazorpayOrderResponseDTO();
            response.setKey(razorpayKeyId);
            response.setOrderId(order.get("id").toString());
            response.setPaymentId(saved.getPaymentId());
            response.setBookingId(saved.getBookingId());
            response.setAmount(amountInPaise);
            response.setCurrency(saved.getCurrency());

            return response;
        } catch (Exception e) {
            throw new RuntimeException("Razorpay order creation failed: " + e.getMessage());
        }
    }

    //Processes payment result after gateway response.
    @Override
    public PaymentResponseDTO processPayment(UUID paymentId, String transactionId, String status) {

        Payment payment = repository.findByPaymentId(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        payment.setTransactionId(transactionId);

        PaymentStatus ps = PaymentStatus.valueOf(status.toUpperCase());
        payment.setStatus(ps);

        payment.setPaidAt(LocalDateTime.now());

        repository.save(payment);

        //AUTO CALLBACK
        if (ps == PaymentStatus.PAID || ps == PaymentStatus.FAILED) {

            bookingClient.callback(
                    payment.getBookingId(),
                    transactionId,
                    ps.name()
            );
        }

        return mapToDTO(payment);
    }

    @Override
    public List<PaymentResponseDTO> getPaymentByBooking(UUID bookingId) {
        return mapList(repository.findByBookingId(bookingId));
    }

    @Override
    public List<PaymentResponseDTO> getPaymentsByUser(Long userId) {
        return mapList(repository.findByUserId(userId));
    }

    @Override
    public PaymentResponseDTO refundPayment(UUID paymentId) {

        Payment payment = repository.findByPaymentId(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        payment.setStatus(PaymentStatus.REFUNDED);
        payment.setRefundAmount(payment.getAmount());
        payment.setRefundedAt(LocalDateTime.now());

        repository.save(payment);

        return mapToDTO(payment);
    }

    @Override
    public String getPaymentStatus(UUID paymentId) {
        return repository.findByPaymentId(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found"))
                .getStatus()
                .name();
    }

    @Override
    public PaymentResponseDTO updatePaymentStatus(UUID paymentId, String status) {

        Payment payment = repository.findByPaymentId(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        payment.setStatus(PaymentStatus.valueOf(status.toUpperCase()));

        repository.save(payment);

        return mapToDTO(payment);
    }

    @Override
    public String generateReceipt(UUID paymentId) {

        Payment p = repository.findByPaymentId(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        return "Receipt\nPaymentId: " + p.getPaymentId() +
                "\nAmount: " + p.getAmount() +
                "\nStatus: " + p.getStatus();
    }

    @Override
    public Double getRevenue() {

        List<Payment> paidPayments = repository.findByStatus(PaymentStatus.PAID);

        double total = 0.0;

        for (Payment p : paidPayments) {
            total += p.getAmount();
        }

        return total;
    }

    //MAPPERS

    private PaymentResponseDTO mapToDTO(Payment p) {

        PaymentResponseDTO dto = new PaymentResponseDTO();

        dto.setPaymentId(p.getPaymentId());
        dto.setBookingId(p.getBookingId());
        dto.setUserId(p.getUserId());
        dto.setAmount(p.getAmount());
        dto.setCurrency(p.getCurrency());
        dto.setStatus(p.getStatus().name());
        dto.setPaymentMode(p.getPaymentMode().name());
        dto.setTransactionId(p.getTransactionId());
        dto.setPaidAt(p.getPaidAt());

        return dto;
    }

    private List<PaymentResponseDTO> mapList(List<Payment> list) {

        List<PaymentResponseDTO> result = new ArrayList<>();

        for (Payment p : list) {
            result.add(mapToDTO(p));
        }

        return result;
    }
}