package com.notification.NotificationService.service.impl;

import com.notification.NotificationService.dto.*;
import com.notification.NotificationService.entity.Notification;
import com.notification.NotificationService.enums.NotificationChannel;
import com.notification.NotificationService.enums.NotificationType;
import com.notification.NotificationService.repository.NotificationRepository;
import com.notification.NotificationService.service.NotificationService;

import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository repository;
    private final JavaMailSender mailSender;

    // Twilio number from application.properties
    @Value("${twilio.phone.number}")
    private String fromNumber;

    // ================= SEND SINGLE =================
    @Override
    public NotificationResponseDTO send(NotificationRequestDTO dto) {

        Notification notification = Notification.builder()
                .recipientId(dto.getRecipientId())
                .type(dto.getType())
                .title(dto.getTitle())
                .message(dto.getMessage())
                .channel(dto.getChannel())
                .relatedBookingId(dto.getRelatedBookingId())
                .isRead(false)
                .build();

        Notification saved = repository.save(notification);

        // Send via channel
        if (dto.getChannel() == NotificationChannel.EMAIL && dto.getEmail() != null) {
            sendEmail(dto.getEmail(), dto.getTitle(), dto.getMessage());
        } else if (dto.getChannel() == NotificationChannel.SMS && dto.getPhoneNumber() != null) {
            sendSMS(dto.getPhoneNumber(), dto.getMessage());
        }

        return mapToDTO(saved);
    }

    // ================= BOOKING CONFIRMATION =================
    @Override
    public NotificationResponseDTO sendBookingConfirmation(NotificationRequestDTO dto) {
        dto.setType(NotificationType.BOOKING_CONFIRMED);
        return send(dto);
    }

    // ================= BULK =================
    @Override
    public List<NotificationResponseDTO> sendBulk(BulkNotificationDTO bulkDTO) {

        return bulkDTO.getRecipientIds().stream()
                .map(recipientId -> {
                    NotificationRequestDTO dto = new NotificationRequestDTO();
                    dto.setRecipientId(recipientId);
                    dto.setType(bulkDTO.getType());
                    dto.setTitle(bulkDTO.getTitle());
                    dto.setMessage(bulkDTO.getMessage());
                    dto.setChannel(bulkDTO.getChannel());
                    dto.setRelatedBookingId(bulkDTO.getRelatedBookingId());
                    return send(dto);
                })
                .collect(Collectors.toList());
    }

    // ================= GET =================
    @Override
    public List<NotificationResponseDTO> getByRecipient(Long recipientId) {
        return repository.findByRecipientId(recipientId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // ================= MARK READ =================
    @Override
    public void markAsRead(Long notificationId) {
        Notification notification = repository.findById(notificationId)
                .orElseThrow(() -> new EntityNotFoundException("Notification not found"));

        notification.setRead(true);
        repository.save(notification);
    }

    @Override
    public void markAllRead(Long recipientId) {
        List<Notification> notifications = repository.findByRecipientId(recipientId);
        notifications.forEach(n -> n.setRead(true));
        repository.saveAll(notifications);
    }

    // ================= COUNT =================
    @Override
    public long getUnreadCount(Long recipientId) {
        return repository.countByRecipientIdAndIsRead(recipientId, false);
    }

    // ================= DELETE =================
    @Override
    public void deleteNotification(Long notificationId) {
        repository.deleteByNotificationId(notificationId);
    }

    // ================= EMAIL =================
    @Override
    public void sendEmail(String to, String subject, String body) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);

        System.out.println("Email sent successfully!");
    }

    // ================= SMS =================
    @Override
    public void sendSMS(String phoneNumber, String message) {

        Message.creator(
                new PhoneNumber(phoneNumber),   // TO
                new PhoneNumber(fromNumber),    // FROM (Twilio number)
                message
        ).create();

        System.out.println("SMS sent successfully!");
    }

    // ================= MAPPER =================
    private NotificationResponseDTO mapToDTO(Notification n) {
        return NotificationResponseDTO.builder()
                .notificationId(n.getNotificationId())
                .recipientId(n.getRecipientId())
                .type(n.getType())
                .title(n.getTitle())
                .message(n.getMessage())
                .channel(n.getChannel())
                .relatedBookingId(n.getRelatedBookingId())
                .isRead(n.isRead())
                .sentAt(n.getSentAt())
                .build();
    }
}