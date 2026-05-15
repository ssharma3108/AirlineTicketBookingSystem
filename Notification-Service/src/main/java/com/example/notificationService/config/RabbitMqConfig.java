package com.example.notificationService.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    @Value("${app.rabbitmq.booking-confirmation-queue:booking.confirmation.queue}")
    private String bookingConfirmationQueue;

    @Value("${app.rabbitmq.booking-exchange:skybooker.booking.exchange}")
    private String bookingExchangeName;

    @Value("${app.rabbitmq.booking-confirmation-routing-key:booking.confirmation}")
    private String bookingRoutingKey;

    @Bean
    public Queue bookingQueue() {
        return QueueBuilder
                .durable(bookingConfirmationQueue)
                .build();
    }

    @Bean
    public TopicExchange bookingExchange() {
        return new TopicExchange(bookingExchangeName, true, false);
    }

    @Bean
    public Binding bookingBinding(Queue bookingQueue, TopicExchange bookingExchange) {
        return BindingBuilder
                .bind(bookingQueue)
                .to(bookingExchange)
                .with(bookingRoutingKey);
    }

    @Bean
    public MessageConverter messageConverter() {
        return new JacksonJsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory,
                                         MessageConverter messageConverter) {

        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter);
        return template;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
            ConnectionFactory connectionFactory,
            MessageConverter messageConverter) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(messageConverter);
        return factory;
    }
}
