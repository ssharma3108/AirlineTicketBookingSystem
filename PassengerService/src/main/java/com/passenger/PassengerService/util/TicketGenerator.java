package com.passenger.PassengerService.util;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class TicketGenerator {

    public String generateTicketNumber() {
        return "TKT-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
