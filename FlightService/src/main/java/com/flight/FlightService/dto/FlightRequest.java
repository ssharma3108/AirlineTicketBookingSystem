// FlightRequest.java
package com.flight.FlightService.dto;

import com.flight.FlightService.entity.FlightStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FlightRequest {

    @NotBlank
    private String flightNumber;

    @NotNull
    private Integer airlineId;

    @NotBlank
    private String originAirportCode;

    @NotBlank
    private String destinationAirportCode;

    @NotNull
    private LocalDateTime departureTime;

    @NotNull
    private LocalDateTime arrivalTime;

    @NotNull
    private Integer durationMinutes;

    @NotBlank
    private String aircraftType;

    @NotNull
    private Integer totalSeats;

    @NotNull
    private Integer availableSeats;

    @NotNull
    private FlightStatus status;
}