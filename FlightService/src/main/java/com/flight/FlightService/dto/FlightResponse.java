// FlightResponse.java
package com.flight.FlightService.dto;

import com.flight.FlightService.entity.FlightStatus;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FlightResponse {

    private Integer id;
    private String flightNumber;
    private Integer airlineId;
    private String originAirportCode;
    private String destinationAirportCode;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private Integer durationMinutes;
    private String aircraftType;
    private Integer totalSeats;
    private Integer availableSeats;
    private FlightStatus status;
}