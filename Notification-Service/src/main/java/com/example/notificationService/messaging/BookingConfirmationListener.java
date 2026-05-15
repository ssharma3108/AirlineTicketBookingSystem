package com.example.notificationService.messaging;

import com.example.notificationService.dto.BookingConfirmationRequestDTO;
import com.example.notificationService.service.NotifService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class BookingConfirmationListener {

    private final NotifService notifService;
    private final String queueName;

    public BookingConfirmationListener(
            NotifService notifService,
            @Value("${app.rabbitmq.booking-confirmation-queue:booking.confirmation.queue}") String queueName
    ) {
        this.notifService = notifService;
        this.queueName = queueName;
    }

    @RabbitListener(queues = "${app.rabbitmq.booking-confirmation-queue:booking.confirmation.queue}")
    public void consumeBookingConfirmation(BookingConfirmationRequestDTO request) {
        if (request == null || request.getUserId() == null || !StringUtils.hasText(request.getBookingId())) {
            System.err.println("Invalid booking confirmation message received. Message skipped.");
            return;
        }

        notifService.sendBookingConfirmation(
                request.getUserId(),
                request.getBookingId(),
                request.getEmail(),
                request.getPhone()
        );
        System.out.println("RabbitMQ consumed booking confirmation from queue: " + queueName);
    }
}
