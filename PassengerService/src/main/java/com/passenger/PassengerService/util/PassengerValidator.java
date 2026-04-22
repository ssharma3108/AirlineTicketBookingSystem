package com.passenger.PassengerService.util;

import com.passenger.PassengerService.dto.PassengerRequestDTO;
import org.springframework.stereotype.Component;

@Component
public class PassengerValidator {

    public void validate(PassengerRequestDTO dto) {
        if (dto.getFirstName() == null || dto.getFirstName().isEmpty()) {
            throw new RuntimeException("First name is required");
        }

        if (dto.getPassportNumber() == null || dto.getPassportNumber().isEmpty()) {
            throw new RuntimeException("Passport number is required");
        }
    }
}