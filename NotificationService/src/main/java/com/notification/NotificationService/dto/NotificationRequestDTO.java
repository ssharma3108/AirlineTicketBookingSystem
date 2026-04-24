package com.notification.NotificationService.dto;

import com.notification.NotificationService.enums.*;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class NotificationRequestDTO {

    @NotNull
    private Long recipientId;

    @NotNull
    private NotificationType type;

    @NotBlank
    private String title;

    @NotBlank
    private String message;

    @NotNull
    private NotificationChannel channel;

    // Optional (used for booking-related notifications)
    private Long relatedBookingId;

    // Optional (for email/SMS sending)
    private String email;
    private String phoneNumber;
}