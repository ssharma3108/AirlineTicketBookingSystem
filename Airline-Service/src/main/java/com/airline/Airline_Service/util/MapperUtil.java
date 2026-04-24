package com.airline.Airline_Service.util;


import com.airline.Airline_Service.dto.*;
import com.airline.Airline_Service.entity.Airline;
import com.airline.Airline_Service.entity.Airport;

public class MapperUtil {

    // ================= AIRLINE =================

    public static AirlineResponseDTO mapToAirlineDTO(Airline airline) {
        return AirlineResponseDTO.builder()
                .airlineId(airline.getAirlineId())
                .name(airline.getName())
                .iataCode(airline.getIataCode())
                .icaoCode(airline.getIcaoCode())
                .logoUrl(airline.getLogoUrl())
                .country(airline.getCountry())
                .contactEmail(airline.getContactEmail())
                .contactPhone(airline.getContactPhone())
                .isActive(airline.getIsActive())
                .build();
    }

    public static Airline mapToAirlineEntity(AirlineRequestDTO dto) {
        return Airline.builder()
                .name(dto.getName())
                .iataCode(dto.getIataCode())
                .icaoCode(dto.getIcaoCode())
                .logoUrl(dto.getLogoUrl())
                .country(dto.getCountry())
                .contactEmail(dto.getContactEmail())
                .contactPhone(dto.getContactPhone())
                .isActive(true)
                .build();
    }

    // ================= AIRPORT =================

    public static AirportResponseDTO mapToAirportDTO(Airport airport) {
        return AirportResponseDTO.builder()
                .airportId(airport.getAirportId())
                .name(airport.getName())
                .iataCode(airport.getIataCode())
                .icaoCode(airport.getIcaoCode())
                .city(airport.getCity())
                .country(airport.getCountry())
                .latitude(airport.getLatitude())
                .longitude(airport.getLongitude())
                .timezone(airport.getTimezone())
                .build();
    }

    public static Airport mapToAirportEntity(AirportRequestDTO dto) {
        return Airport.builder()
                .name(dto.getName())
                .iataCode(dto.getIataCode())
                .icaoCode(dto.getIcaoCode())
                .city(dto.getCity())
                .country(dto.getCountry())
                .latitude(dto.getLatitude())
                .longitude(dto.getLongitude())
                .timezone(dto.getTimezone())
                .build();
    }
}