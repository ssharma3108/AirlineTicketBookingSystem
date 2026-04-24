package com.notification.NotificationService.controller;

import com.notification.NotificationService.dto.*;
import com.notification.NotificationService.service.NotificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    // ================= SEND SINGLE =================
    @PostMapping("/send")
    public NotificationResponseDTO sendNotification(
            @Valid @RequestBody NotificationRequestDTO requestDTO) {

        return notificationService.send(requestDTO);
    }

    // ================= BOOKING CONFIRMATION =================
    @PostMapping("/booking-confirmation")
    public NotificationResponseDTO sendBookingConfirmation(
            @Valid @RequestBody NotificationRequestDTO requestDTO) {

        return notificationService.sendBookingConfirmation(requestDTO);
    }

    // ================= BULK =================
    @PostMapping("/send-bulk")
    public List<NotificationResponseDTO> sendBulk(
            @Valid @RequestBody BulkNotificationDTO bulkDTO) {

        return notificationService.sendBulk(bulkDTO);
    }

    // ================= GET BY RECIPIENT =================
    @GetMapping("/recipient/{recipientId}")
    public List<NotificationResponseDTO> getByRecipient(
            @PathVariable Long recipientId) {

        return notificationService.getByRecipient(recipientId);
    }

    // ================= GET UNREAD COUNT =================
    @GetMapping("/recipient/{recipientId}/unread-count")
    public long getUnreadCount(@PathVariable Long recipientId) {
        return notificationService.getUnreadCount(recipientId);
    }

    // ================= MARK AS READ =================
    @PutMapping("/read/{notificationId}")
    public String markAsRead(@PathVariable Long notificationId) {
        notificationService.markAsRead(notificationId);
        return "Notification marked as read";
    }

    // ================= MARK ALL AS READ =================
    @PutMapping("/read-all/{recipientId}")
    public String markAllRead(@PathVariable Long recipientId) {
        notificationService.markAllRead(recipientId);
        return "All notifications marked as read";
    }

    // ================= DELETE =================
    @DeleteMapping("/{notificationId}")
    public String deleteNotification(@PathVariable Long notificationId) {
        notificationService.deleteNotification(notificationId);
        return "Notification deleted successfully";
    }

    // ================= GET ALL (ADMIN) =================
    @GetMapping("/all")
    public List<NotificationResponseDTO> getAll() {
        // You can directly call repository if needed
        return notificationService.getByRecipient(null);
    }

    // ================= TEST EMAIL =================
    @PostMapping("/test-email")
    public String testEmail() {
        notificationService.sendEmail(
                "receiver@gmail.com",
                "Test Email",
                "Hello from Notification Service 🚀"
        );
        return "Email Sent!";
    }

    // ================= TEST SMS =================
    @PostMapping("/test-sms")
    public String testSMS() {
        notificationService.sendSMS(
                "+91XXXXXXXXXX",
                "Test SMS from Notification Service 🚀"
        );
        return "SMS Sent!";
    }
}
