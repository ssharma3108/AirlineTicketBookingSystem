package com.notification.NotificationService.util;

import com.notification.NotificationService.dto.NotificationRequestDTO;
import com.notification.NotificationService.dto.NotificationResponseDTO;
import com.notification.NotificationService.entity.Notification;

public class NotificationMapper {

    // Convert RequestDTO → Entity
    public static Notification toEntity(NotificationRequestDTO dto) {
        return Notification.builder()
                .recipientId(dto.getRecipientId())
                .type(dto.getType())
                .title(dto.getTitle())
                .message(dto.getMessage())
                .channel(dto.getChannel())
                .relatedBookingId(dto.getRelatedBookingId())
                .isRead(false)
                .build();
    }

    // Convert Entity → ResponseDTO
    public static NotificationResponseDTO toDTO(Notification entity) {
        return NotificationResponseDTO.builder()
                .notificationId(entity.getNotificationId())
                .recipientId(entity.getRecipientId())
                .type(entity.getType())
                .title(entity.getTitle())
                .message(entity.getMessage())
                .channel(entity.getChannel())
                .relatedBookingId(entity.getRelatedBookingId())
                .isRead(entity.isRead())
                .sentAt(entity.getSentAt())
                .build();
    }
}