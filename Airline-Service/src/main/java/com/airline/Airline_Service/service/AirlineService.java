package com.airline.Airline_Service.service;


import com.airline.Airline_Service.dto.AirlineRequestDTO;
import com.airline.Airline_Service.dto.AirlineResponseDTO;

import java.util.List;

public interface AirlineService {

    AirlineResponseDTO createAirline(AirlineRequestDTO dto);

    AirlineResponseDTO getAirlineById(Long id);

    AirlineResponseDTO getAirlineByIata(String iata);

    List<AirlineResponseDTO> getAllAirlines();

    AirlineResponseDTO updateAirline(Long id, AirlineRequestDTO dto);

    void deactivateAirline(Long id);
}