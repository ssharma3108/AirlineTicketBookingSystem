package com.paynment.PaynmentService.util;

import com.paynment.PaynmentService.dto.ReceiptDTO;
import com.paynment.PaynmentService.entity.Payment;

public class ReceiptGenerator {

    public static ReceiptDTO generateReceipt(Payment payment) {

        return ReceiptDTO.builder()
                .paymentId(payment.getPaymentId())
                .bookingId(payment.getBookingId())
                .amount(payment.getAmount())
                .currency(payment.getCurrency())
                .status(payment.getStatus())
                .transactionId(payment.getTransactionId())
                .paidAt(payment.getPaidAt())
                .refundedAt(payment.getRefundedAt())
                .refundAmount(payment.getRefundAmount())
                .build();
    }
}