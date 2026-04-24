package com.airline.Airline_Service.service.impl;

import com.airline.Airline_Service.dto.*;
import com.airline.Airline_Service.entity.Airport;
import com.airline.Airline_Service.exceptions.ResourceNotFoundException;
import com.airline.Airline_Service.repository.AirportRepository;
import com.airline.Airline_Service.service.AirportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AirportServiceImpl implements AirportService {

    private final AirportRepository repository;

    @Override
    public AirportResponseDTO createAirport(AirportRequestDTO dto) {
        Airport airport = Airport.builder()
                .name(dto.getName())
                .iataCode(dto.getIataCode())
                .icaoCode(dto.getIcaoCode())
                .city(dto.getCity())
                .country(dto.getCountry())
                .latitude(dto.getLatitude())
                .longitude(dto.getLongitude())
                .timezone(dto.getTimezone())
                .build();

        return mapToDTO(repository.save(airport));
    }

    @Override
    public AirportResponseDTO getAirportByIata(String iata) {
        Airport airport = repository.findByIataCode(iata)
                .orElseThrow(() -> new ResourceNotFoundException("Airport not found"));
        return mapToDTO(airport);
    }

    @Override
    public List<AirportResponseDTO> getAirportsByCity(String city) {
        return repository.findByCity(city)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<AirportResponseDTO> getAirportsByCountry(String country) {
        return repository.findByCountry(country)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<AirportResponseDTO> searchAirports(String keyword) {
        return repository.findByNameContainingIgnoreCase(keyword)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public AirportResponseDTO updateAirport(Long id, AirportRequestDTO dto) {
        Airport airport = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Airport not found"));

        airport.setName(dto.getName());
        airport.setIataCode(dto.getIataCode());
        airport.setIcaoCode(dto.getIcaoCode());
        airport.setCity(dto.getCity());
        airport.setCountry(dto.getCountry());
        airport.setLatitude(dto.getLatitude());
        airport.setLongitude(dto.getLongitude());
        airport.setTimezone(dto.getTimezone());

        return mapToDTO(repository.save(airport));
    }

    private AirportResponseDTO mapToDTO(Airport airport) {
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
}