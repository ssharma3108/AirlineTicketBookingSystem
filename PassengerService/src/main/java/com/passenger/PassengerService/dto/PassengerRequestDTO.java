package com.passenger.PassengerService.dto;

import com.passenger.PassengerService.enums.Gender;
import com.passenger.PassengerService.enums.PassengerType;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PassengerRequestDTO {

    private Long bookingId;
    private String title;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private Gender gender;
    private String passportNumber;
    private String nationality;
    private LocalDate passportExpiry;
    private PassengerType passengerType;
}