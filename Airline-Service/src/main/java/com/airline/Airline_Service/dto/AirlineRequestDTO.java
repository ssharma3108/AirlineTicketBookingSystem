package com.airline.Airline_Service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AirlineRequestDTO {

    @NotBlank
    private String name;

    @NotBlank
    private String iataCode;

    @NotBlank
    private String icaoCode;

    private String logoUrl;
    private String country;

    @Email
    private String contactEmail;

    private String contactPhone;
}