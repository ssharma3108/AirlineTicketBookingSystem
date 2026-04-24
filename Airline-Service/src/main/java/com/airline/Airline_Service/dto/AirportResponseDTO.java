package com.airline.Airline_Service.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AirportResponseDTO {

    private Long airportId;
    private String name;
    private String iataCode;
    private String icaoCode;
    private String city;
    private String country;
    private Double latitude;
    private Double longitude;
    private String timezone;
}