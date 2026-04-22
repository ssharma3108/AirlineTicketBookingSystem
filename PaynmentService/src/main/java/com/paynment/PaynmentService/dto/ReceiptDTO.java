package com.paynment.PaynmentService.dto;

import com.paynment.PaynmentService.enums.PaymentStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder   // 🔥 THIS IS REQUIRED
public class ReceiptDTO {

    private UUID paymentId;
    private Long bookingId;
    private Double amount;
    private String currency;

    private PaymentStatus status;

    private String transactionId;

    private LocalDateTime paidAt;
    private LocalDateTime refundedAt;

    private Double refundAmount;
}
