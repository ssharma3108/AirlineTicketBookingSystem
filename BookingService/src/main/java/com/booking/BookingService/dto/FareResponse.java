package com.booking.BookingService.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FareResponse {

    private Double baseFare;
    private Double taxes;
    private Double luggageCharge;
    private Double totalFare;
}