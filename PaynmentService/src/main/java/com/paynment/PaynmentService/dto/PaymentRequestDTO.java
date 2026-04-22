package com.paynment.PaynmentService.dto;

import com.paynment.PaynmentService.enums.PaymentMode;
import lombok.Data;

@Data
public class PaymentRequestDTO {
    private Long bookingId;
    private Long userId;
    private Double amount;
    private String currency;
    private PaymentMode paymentMode;
}
