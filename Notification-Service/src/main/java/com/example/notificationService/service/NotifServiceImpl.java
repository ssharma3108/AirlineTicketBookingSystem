package com.example.notificationService.service;

import com.example.notificationService.entity.Channel;
import com.example.notificationService.entity.Notification;
import com.example.notificationService.entity.NotificationType;
import com.example.notificationService.repository.NotificationRepository;

import jakarta.transaction.Transactional;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotifServiceImpl implements NotifService {

    private final NotificationRepository repo;
    private final JavaMailSender mailSender;

    public NotifServiceImpl(NotificationRepository repo, JavaMailSender mailSender) {
        this.repo = repo;
        this.mailSender = mailSender;
    }

    @Override
    public Notification send(Notification notification) {
        if (notification == null) {
            throw new IllegalArgumentException("Notification is required");
        }
        if (notification.getIsRead() == null) {
            notification.setIsRead(false);
        }
        notification.setSentAt(LocalDateTime.now());
        return repo.save(notification);
    }

    @Override
    public void sendBookingConfirmation(Long userId, String bookingId, String email, String phone) {
        if (userId == null) {
            throw new IllegalArgumentException("userId is required");
        }
        if (!StringUtils.hasText(bookingId)) {
            throw new IllegalArgumentException("bookingId is required");
        }

        Notification appNotif = new Notification();
        appNotif.setRecipientId(userId);
        appNotif.setType(NotificationType.BOOKING_CONFIRMED);
        appNotif.setChannel(Channel.APP);
        appNotif.setTitle("Booking Confirmed");
        appNotif.setMessage("Your booking is confirmed. PNR: " + bookingId);
        appNotif.setRelatedBookingId(bookingId);

        send(appNotif);

        sendEmail(email, "Booking Confirmed", "Your booking is confirmed. PNR: " + bookingId);
        sendSMS(phone, "Booking confirmed. PNR: " + bookingId);
    }

    @Override
    public void sendBulk(List<Notification> notifications) {
        for (Notification n : notifications) {
            send(n);
        }
    }

    @Override
    public Notification markAsRead(Long notificationId) {
        Notification notif = repo.findById(notificationId)
                .orElseThrow(() -> new RuntimeException("Not found"));
        notif.setIsRead(true);
        return repo.save(notif);
    }

    @Override
    public void markAllRead(Long recipientId) {
        List<Notification> list = repo.findByRecipientId(recipientId);
        for (Notification n : list) {
            n.setIsRead(true);
        }
        repo.saveAll(list);
    }

    @Override
    public List<Notification> getByRecipient(Long recipientId) {
        return repo.findByRecipientId(recipientId);
    }

    @Override
    public long getUnreadCount(Long recipientId) {
        return repo.countByRecipientIdAndIsRead(recipientId, false);
    }

    @Override
    @Transactional
    public void deleteNotification(Long notificationId) {
        repo.deleteByNotificationId(notificationId);
    }

    // Helper Methods
    public void sendEmail(String email, String subject, String body) {
        if (!StringUtils.hasText(email)) {
            return;
        }

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject(subject);
        message.setText(body);

        try {
            mailSender.send(message);
            System.out.println("Email sent to " + email + " | " + subject);
        } catch (MailException ex) {
            System.err.println("Email failed for " + email + ": " + ex.getMessage());
        }
    }

    public void sendSMS(String phone, String message) {
        if (!StringUtils.hasText(phone)) {
            return;
        }
        System.out.println("SMS sent to " + phone + " | " + message);
    }
}
