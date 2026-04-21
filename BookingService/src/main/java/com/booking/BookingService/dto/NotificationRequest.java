package com.booking.BookingService.dto;

import lombok.Data;

@Data
public class NotificationRequest {

    private String email;
    private String phone;
    private String subject;
    private String message;
}