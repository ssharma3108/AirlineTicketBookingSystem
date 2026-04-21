package com.booking.BookingService.dto;

import lombok.Data;

@Data
public class PaymentRequest {

    private String bookingId;
    private Double amount;
    private String paymentMethod;
}