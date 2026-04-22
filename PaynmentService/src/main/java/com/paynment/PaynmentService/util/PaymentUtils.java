package com.paynment.PaynmentService.util;

import java.util.UUID;

public class PaymentUtils {

    // Generate unique transaction ID (fallback if needed)
    public static String generateTransactionId() {
        return "TXN-" + UUID.randomUUID().toString();
    }

    // Convert amount to smallest currency unit (₹ → paise)
    public static Long convertToSubUnit(Double amount) {
        if (amount == null) return 0L;
        return (long) (amount * 100);
    }

    // Basic validation
    public static void validateAmount(Double amount) {
        if (amount == null || amount <= 0) {
            throw new IllegalArgumentException("Invalid payment amount");
        }
    }
}
