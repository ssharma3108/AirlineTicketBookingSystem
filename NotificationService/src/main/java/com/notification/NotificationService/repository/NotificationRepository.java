package com.notification.NotificationService.repository;


import com.notification.NotificationService.entity.*;
import com.notification.NotificationService.enums.*;
import org.springframework.data.jpa.repository.*;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findByRecipientId(Long recipientId);

    List<Notification> findByRecipientIdAndIsRead(Long recipientId, boolean isRead);

    long countByRecipientIdAndIsRead(Long recipientId, boolean isRead);

    List<Notification> findByType(NotificationType type);

    List<Notification> findByRelatedBookingId(Long bookingId);

    void deleteByNotificationId(Long notificationId);
}
