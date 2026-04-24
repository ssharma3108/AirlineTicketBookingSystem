package com.airline.Airline_Service.service.impl;

import com.airline.Airline_Service.dto.*;
import com.airline.Airline_Service.entity.Airline;
import com.airline.Airline_Service.exceptions.ResourceNotFoundException;
import com.airline.Airline_Service.repository.AirlineRepository;
import com.airline.Airline_Service.service.AirlineService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AirlineServiceImpl implements AirlineService {

    private final AirlineRepository repository;

    @Override
    public AirlineResponseDTO createAirline(AirlineRequestDTO dto) {
        Airline airline = Airline.builder()
                .name(dto.getName())
                .iataCode(dto.getIataCode())
                .icaoCode(dto.getIcaoCode())
                .logoUrl(dto.getLogoUrl())
                .country(dto.getCountry())
                .contactEmail(dto.getContactEmail())
                .contactPhone(dto.getContactPhone())
                .isActive(true)
                .build();

        return mapToDTO(repository.save(airline));
    }

    @Override
    public AirlineResponseDTO getAirlineById(Long id) {
        Airline airline = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Airline not found"));
        return mapToDTO(airline);
    }

    @Override
    public AirlineResponseDTO getAirlineByIata(String iata) {
        Airline airline = repository.findByIataCode(iata)
                .orElseThrow(() -> new ResourceNotFoundException("Airline not found"));
        return mapToDTO(airline);
    }

    @Override
    public List<AirlineResponseDTO> getAllAirlines() {
        return repository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public AirlineResponseDTO updateAirline(Long id, AirlineRequestDTO dto) {
        Airline airline = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Airline not found"));

        airline.setName(dto.getName());
        airline.setIataCode(dto.getIataCode());
        airline.setIcaoCode(dto.getIcaoCode());
        airline.setLogoUrl(dto.getLogoUrl());
        airline.setCountry(dto.getCountry());
        airline.setContactEmail(dto.getContactEmail());
        airline.setContactPhone(dto.getContactPhone());

        return mapToDTO(repository.save(airline));
    }

    @Override
    public void deactivateAirline(Long id) {
        Airline airline = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Airline not found"));

        airline.setIsActive(false);
        repository.save(airline);
    }

    private AirlineResponseDTO mapToDTO(Airline airline) {
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
}