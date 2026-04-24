package com.notification.NotificationService.entity;

import com.notification.NotificationService.enums.*;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long notificationId;

    // User ID (from Passenger/Auth Service)
    @Column(nullable = false)
    private Long recipientId;

    // Type of notification
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NotificationType type;

    // Title (short heading)
    @Column(nullable = false)
    private String title;

    // Message body
    @Column(nullable = false, length = 1000)
    private String message;

    // Channel: EMAIL / SMS / APP
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NotificationChannel channel;

    // Related booking ID (optional)
    private Long relatedBookingId;

    // Read status
    @Column(nullable = false)
    private boolean isRead = false;

    // When notification was sent
    @Column(nullable = false)
    private LocalDateTime sentAt;

    // Automatically set timestamp before saving
    @PrePersist
    public void prePersist() {
        this.sentAt = LocalDateTime.now();
    }
}