package com.example.airlineService.service;

import com.example.airlineService.dto.*;

import java.util.List;

public interface AirlineService {

    AirlineDTO createAirline(AirlineDTO dto);
    AirlineDTO getAirlineById(Long id);
    AirlineDTO getAirlineByIata(String iata);
    List<AirlineDTO> getAllAirlines();
    AirlineDTO updateAirline(Long id, AirlineDTO dto);
    void deactivateAirline(Long id);

    AirportDTO createAirport(AirportDTO dto);
    AirportDTO getAirportByIata(String iata);
    List<AirportDTO> searchAirports(String keyword);
    List<AirportDTO> getAirportsByCity(String city);
    List<AirportDTO> getAirportsByCountry(String country);
    AirportDTO updateAirport(Long id, AirportDTO dto);
}