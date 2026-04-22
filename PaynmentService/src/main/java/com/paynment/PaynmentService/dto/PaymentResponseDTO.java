package com.paynment.PaynmentService.dto;

import com.paynment.PaynmentService.enums.PaymentStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class PaymentResponseDTO {
    private UUID paymentId;
    private PaymentStatus status;
    private String transactionId;
    private LocalDateTime paidAt;
    private String clientSecret;
}
