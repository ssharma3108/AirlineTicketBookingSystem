package com.example.bookingService.entity;

public enum RefundStatus {
    NON_REFUNDED,  // default: no refund requested
    REQUESTED,     // user has requested refund
    REFUNDED,      // admin approved + refund completed
    REJECTED       // admin rejected refund request
}
