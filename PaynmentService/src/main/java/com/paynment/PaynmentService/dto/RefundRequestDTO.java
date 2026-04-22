package com.paynment.PaynmentService.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class RefundRequestDTO {
    private UUID paymentId;
    private Double refundAmount;
}
