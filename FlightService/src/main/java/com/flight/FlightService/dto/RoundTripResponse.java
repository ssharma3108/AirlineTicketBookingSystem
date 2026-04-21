// RoundTripResponse.java
package com.flight.FlightService.dto;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RoundTripResponse {

    private List<FlightResponse> onwardFlights;

    private List<FlightResponse> returnFlights;
}