package com.passenger.PassengerService.entity;

import com.passenger.PassengerService.enums.Gender;
import com.passenger.PassengerService.enums.PassengerType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "passenger")
public class Passenger {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long passengerId;

    private Long bookingId;

    private String title;
    private String firstName;
    private String lastName;

    private LocalDate dateOfBirth;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String passportNumber;
    private String nationality;
    private LocalDate passportExpiry;

    private Long seatId;
    private String seatNumber;

    private String ticketNumber;

    @Enumerated(EnumType.STRING)
    private PassengerType passengerType;
}
