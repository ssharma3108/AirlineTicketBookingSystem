package com.example.notificationService.controller;

import com.example.notificationService.dto.BookingConfirmationRequestDTO;
import com.example.notificationService.entity.Notification;
import com.example.notificationService.messaging.BookingConfirmationProducer;
import com.example.notificationService.repository.NotificationRepository;
import com.example.notificationService.service.NotifService;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotifResource {

    private final NotifService service;
    private final NotificationRepository repo;
    private final BookingConfirmationProducer bookingConfirmationProducer;

    public NotifResource(NotifService service,
                         NotificationRepository repo,
                         BookingConfirmationProducer bookingConfirmationProducer) {
        this.service = service;
        this.repo = repo;
        this.bookingConfirmationProducer = bookingConfirmationProducer;
    }

    @GetMapping("/user/{recipientId}")
    public List<Notification> getByRecipient(@PathVariable Long recipientId) {
        return service.getByRecipient(recipientId);
    }

    @PutMapping("/read/{notificationId}")
    public Notification markAsRead(@PathVariable Long notificationId) {
        return service.markAsRead(notificationId);
    }

    @PutMapping("/read-all/{recipientId}")
    public String markAllRead(@PathVariable Long recipientId) {
        service.markAllRead(recipientId);
        return "All notifications marked as read";
    }

    @GetMapping("/unread-count/{recipientId}")
    public long getUnreadCount(@PathVariable Long recipientId) {
        return service.getUnreadCount(recipientId);
    }

    @DeleteMapping("/{notificationId}")
    public String delete(@PathVariable Long notificationId) {
        service.deleteNotification(notificationId);
        return "Notification deleted";
    }

    @PostMapping("/bulk")
    public String sendBulk(@RequestBody List<Notification> notifications) {
        service.sendBulk(notifications);
        return "Bulk notifications sent";
    }

    @GetMapping("/all")
    public List<Notification> getAll() {
        return repo.findAll();
    }

    // Async via RabbitMQ
    @PostMapping("/booking-confirmation")
    public String sendBookingConfirmation(@RequestBody BookingConfirmationRequestDTO request) {
        validateBookingConfirmation(request);
        bookingConfirmationProducer.publish(request);
        return "Booking confirmation pushed to RabbitMQ queue";
    }

    // Direct fallback
    @PostMapping("/booking-confirmation/direct")
    public String sendBookingConfirmationDirect(@RequestBody BookingConfirmationRequestDTO request) {
        validateBookingConfirmation(request);
        service.sendBookingConfirmation(
                request.getUserId(),
                request.getBookingId(),
                request.getEmail(),
                request.getPhone()
        );
        return "Booking confirmation notification sent directly";
    }

    private void validateBookingConfirmation(BookingConfirmationRequestDTO request) {
        if (request == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Request body is required");
        }
        if (request.getUserId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "userId is required");
        }
        if (request.getBookingId() == null || request.getBookingId().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "bookingId is required");
        }
        if ((request.getEmail() == null || request.getEmail().isBlank())
                && (request.getPhone() == null || request.getPhone().isBlank())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "email or phone is required");
        }
    }
}
