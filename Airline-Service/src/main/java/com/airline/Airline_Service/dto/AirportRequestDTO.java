package com.airline.Airline_Service.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AirportRequestDTO {

    @NotBlank
    private String name;

    @NotBlank
    private String iataCode;

    private String icaoCode;

    private String city;
    private String country;

    private Double latitude;
    private Double longitude;

    private String timezone;
}