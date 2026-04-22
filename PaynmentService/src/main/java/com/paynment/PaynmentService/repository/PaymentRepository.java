package com.paynment.PaynmentService.repository;

import com.paynment.PaynmentService.entity.Payment;
import com.paynment.PaynmentService.enums.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface PaymentRepository extends JpaRepository<Payment, UUID> {

    List<Payment> findByBookingId(Long bookingId);

    List<Payment> findByUserId(Long userId);

    List<Payment> findByStatus(PaymentStatus status);

    Payment findByTransactionId(String transactionId);

    List<Payment> findByPaidAtBetween(LocalDateTime start, LocalDateTime end);

}
