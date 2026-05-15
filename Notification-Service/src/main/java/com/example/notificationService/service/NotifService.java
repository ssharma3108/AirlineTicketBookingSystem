package com.example.notificationService.service;

import com.example.notificationService.entity.Notification;
import java.util.List;

public interface NotifService {

    Notification send(Notification notification);

    void sendBookingConfirmation(Long userId, String bookingId, String email, String phone);

    void sendBulk(List<Notification> notifications);

    Notification markAsRead(Long notificationId);

    void markAllRead(Long recipientId);

    List<Notification> getByRecipient(Long recipientId);

    long getUnreadCount(Long recipientId);

    void deleteNotification(Long notificationId);
}