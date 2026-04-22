package com.paynment.PaynmentService.entity;


import com.paynment.PaynmentService.enums.PaymentMode;
import com.paynment.PaynmentService.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "payments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {

    @Id
    @GeneratedValue
    private UUID paymentId;

    private Long bookingId;
    private Long userId;

    private Double amount;
    private String currency;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    @Enumerated(EnumType.STRING)
    private PaymentMode paymentMode;

    private String transactionId;

    @Column(columnDefinition = "TEXT")
    private String gatewayResponse;

    private LocalDateTime paidAt;
    private LocalDateTime refundedAt;

    private Double refundAmount;
}
