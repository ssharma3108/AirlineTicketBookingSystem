// StatusUpdateRequest.java
package com.flight.FlightService.dto;

import com.flight.FlightService.entity.FlightStatus;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StatusUpdateRequest {

    @NotNull
    private FlightStatus status;
}