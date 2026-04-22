package com.passenger.PassengerService.dto;

import lombok.Data;

@Data
public class SeatResponse {

    private Long seatId;
    private String seatNumber;
    private String status; // AVAILABLE, BOOKED
}