package com.notification.NotificationService.service;

import com.notification.NotificationService.dto.*;

import java.util.List;

public interface NotificationService {

    // Send single notification
    NotificationResponseDTO send(NotificationRequestDTO requestDTO);

    // Booking confirmation trigger
    NotificationResponseDTO sendBookingConfirmation(NotificationRequestDTO requestDTO);

    // Bulk notification
    List<NotificationResponseDTO> sendBulk(BulkNotificationDTO bulkDTO);

    // Get notifications
    List<NotificationResponseDTO> getByRecipient(Long recipientId);

    // Mark single as read
    void markAsRead(Long notificationId);

    // Mark all as read
    void markAllRead(Long recipientId);

    // Unread count
    long getUnreadCount(Long recipientId);

    // Delete notification
    void deleteNotification(Long notificationId);

    // Internal helpers
    void sendEmail(String to, String subject, String body);

    void sendSMS(String phoneNumber, String message);
}
