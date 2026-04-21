package com.booking.BookingService.dto;

import com.booking.BookingService.enums.TripType;
import lombok.Data;

import java.util.UUID;

@Data
public class BookingRequest {

    private UUID userId;
    private UUID flightId;

    private TripType tripType;

    private String mealPreference;
    private Integer luggageKg;

    private String contactEmail;
    private String contactPhone;
}