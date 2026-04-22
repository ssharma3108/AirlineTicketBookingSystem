package com.passenger.PassengerService.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PassengerResponseDTO {

    private Long passengerId;
    private String fullName;
    private String ticketNumber;
    private String seatNumber;
}
