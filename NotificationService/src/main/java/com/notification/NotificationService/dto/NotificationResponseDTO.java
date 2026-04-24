package com.notification.NotificationService.dto;


import com.notification.NotificationService.enums.*;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class NotificationResponseDTO {

    private Long notificationId;
    private Long recipientId;
    private NotificationType type;
    private String title;
    private String message;
    private NotificationChannel channel;
    private Long relatedBookingId;
    private boolean isRead;
    private LocalDateTime sentAt;
}
