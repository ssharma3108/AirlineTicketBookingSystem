package com.notification.NotificationService.dto;

import com.notification.NotificationService.enums.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.List;

@Data
public class BulkNotificationDTO {

    @NotNull
    private List<Long> recipientIds;

    @NotNull
    private NotificationType type;

    @NotBlank
    private String title;

    @NotBlank
    private String message;

    @NotNull
    private NotificationChannel channel;

    private Long relatedBookingId;
}