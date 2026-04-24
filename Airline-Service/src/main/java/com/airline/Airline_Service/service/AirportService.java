package com.airline.Airline_Service.service;


import com.airline.Airline_Service.dto.AirportRequestDTO;
import com.airline.Airline_Service.dto.AirportResponseDTO;

import java.util.List;

public interface AirportService {

    AirportResponseDTO createAirport(AirportRequestDTO dto);

    AirportResponseDTO getAirportByIata(String iata);

    List<AirportResponseDTO> getAirportsByCity(String city);

    List<AirportResponseDTO> getAirportsByCountry(String country);

    List<AirportResponseDTO> searchAirports(String keyword);

    AirportResponseDTO updateAirport(Long id, AirportRequestDTO dto);
}