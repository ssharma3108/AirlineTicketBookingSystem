package com.airline.Airline_Service.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AirlineResponseDTO {

    private Long airlineId;
    private String name;
    private String iataCode;
    private String icaoCode;
    private String logoUrl;
    private String country;
    private String contactEmail;
    private String contactPhone;
    private Boolean isActive;
}