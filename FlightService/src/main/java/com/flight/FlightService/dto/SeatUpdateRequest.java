// SeatUpdateRequest.java
package com.flight.FlightService.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SeatUpdateRequest {

    @NotNull
    @Min(1)
    private Integer seats;
}