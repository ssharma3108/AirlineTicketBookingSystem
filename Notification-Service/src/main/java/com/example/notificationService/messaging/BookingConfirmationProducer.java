package com.example.notificationService.messaging;

import com.example.notificationService.dto.BookingConfirmationRequestDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class BookingConfirmationProducer {

    private final RabbitTemplate rabbitTemplate;
    private final String bookingExchange;
    private final String bookingConfirmationRoutingKey;

    public BookingConfirmationProducer(
            RabbitTemplate rabbitTemplate,
            @Value("${app.rabbitmq.booking-exchange:skybooker.booking.exchange}") String bookingExchange,
            @Value("${app.rabbitmq.booking-confirmation-routing-key:booking.confirmation}") String bookingConfirmationRoutingKey
    ) {
        this.rabbitTemplate = rabbitTemplate;
        this.bookingExchange = bookingExchange;
        this.bookingConfirmationRoutingKey = bookingConfirmationRoutingKey;
    }

    public void publish(BookingConfirmationRequestDTO payload) {
        if (payload == null) {
            throw new IllegalArgumentException("Booking confirmation payload is required");
        }
        rabbitTemplate.convertAndSend(bookingExchange, bookingConfirmationRoutingKey, payload);
    }
}
