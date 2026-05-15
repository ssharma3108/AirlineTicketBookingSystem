package com.example.notificationService.service;

import com.example.notificationService.dto.BookingConfirmationRequestDTO;
import org.junit.jupiter.api.Test;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.SimpleMailMessage;

import static org.mockito.Mockito.*;

class NotifServiceImplTest {

    @Test
    void sendBookingConfirmation_sendsMail() {
        JavaMailSender sender = mock(JavaMailSender.class);


        BookingConfirmationRequestDTO req = new BookingConfirmationRequestDTO();
        req.setEmail("user@test.com");



        verify(sender, times(1)).send(any(SimpleMailMessage.class));
    }
}
