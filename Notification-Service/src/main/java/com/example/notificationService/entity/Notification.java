package com.example.notificationService.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long notificationId;

    private Long recipientId;

    @Enumerated(EnumType.STRING)
    private NotificationType type;

    private String title;
    private String message;

    @Enumerated(EnumType.STRING)
    private Channel channel;

    private String relatedBookingId;

    private Boolean isRead;

    private LocalDateTime sentAt;

    public Notification() {
        this.isRead = false;
    }

    public Notification(Long recipientId, NotificationType type, String title,
                        String message, Channel channel, String relatedBookingId) {
        this.recipientId = recipientId;
        this.type = type;
        this.title = title;
        this.message = message;
        this.channel = channel;
        this.relatedBookingId = relatedBookingId;
        this.isRead = false;
        this.sentAt = LocalDateTime.now();
    }


    public Long getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(Long notificationId) {
        this.notificationId = notificationId;
    }

    public Long getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(Long recipientId) {
        this.recipientId = recipientId;
    }

    public NotificationType getType() {
        return type;
    }

    public void setType(NotificationType type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public String getRelatedBookingId() {
        return relatedBookingId;
    }

    public void setRelatedBookingId(String relatedBookingId) {
        this.relatedBookingId = relatedBookingId;
    }

    public Boolean getIsRead() {
        return isRead;
    }

    public void setIsRead(Boolean isRead) {
        this.isRead = isRead;
    }

    public LocalDateTime getSentAt() {
        return sentAt;
    }

    public void setSentAt(LocalDateTime sentAt) {
        this.sentAt = sentAt;
    }
}