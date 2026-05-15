package com.example.notificationService.scheduler;

import com.example.notificationService.service.NotifService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class NotificationScheduler {

    private final NotifService service;

    public NotificationScheduler(NotifService service) {
        this.service = service;
    }

    @Scheduled(cron = "0 0 * * * *") // every hour
    public void sendCheckInReminders() {
        System.out.println("Running check-in reminder job...");
        //Booking-Service call
    }
}