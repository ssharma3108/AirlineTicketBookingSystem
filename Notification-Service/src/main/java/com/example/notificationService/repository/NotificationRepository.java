package com.example.notificationService.repository;

import com.example.notificationService.entity.Notification;
import com.example.notificationService.entity.NotificationType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findByRecipientId(Long recipientId);

    List<Notification> findByRecipientIdAndIsRead(Long recipientId, Boolean isRead);

    long countByRecipientIdAndIsRead(Long recipientId, Boolean isRead);

    List<Notification> findByType(NotificationType type);

    List<Notification> findByRelatedBookingId(String relatedBookingId);

    void deleteByNotificationId(Long notificationId);
}