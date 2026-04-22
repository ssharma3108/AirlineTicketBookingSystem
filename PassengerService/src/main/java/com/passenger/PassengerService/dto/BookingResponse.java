package com.passenger.PassengerService.dto;

import lombok.Data;

@Data
public class BookingResponse {

    private Long bookingId;
    private String status;  // CONFIRMED, CANCELLED
}
