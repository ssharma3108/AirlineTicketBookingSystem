package com.example.paymentService.service;

import com.example.paymentService.dto.PaymentRequestDTO;
import com.example.paymentService.entity.Payment;
import com.example.paymentService.repository.PaymentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceImplTest {

    @Mock PaymentRepository repo;
    @InjectMocks PaymentServiceImpl service;

    @Test
    void initiatePayment_createsPending() {
        PaymentRequestDTO req = new PaymentRequestDTO();
        req.setBookingId(java.util.UUID.randomUUID()); req.setUserId(1L); req.setAmount(5000.0); req.setCurrency("INR");
        when(repo.save(any(Payment.class))).thenAnswer(i -> i.getArgument(0));

        var res = service.initiatePayment(req);
        assertNotNull(res.getPaymentId());
        assertEquals("PENDING", res.getStatus().toUpperCase());
    }
}
