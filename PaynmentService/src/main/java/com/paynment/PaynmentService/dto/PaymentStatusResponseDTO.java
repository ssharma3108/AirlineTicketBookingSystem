package com.paynment.PaynmentService.dto;

import com.paynment.PaynmentService.enums.PaymentStatus;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class PaymentStatusResponseDTO {

    private UUID paymentId;
    private PaymentStatus status;
}