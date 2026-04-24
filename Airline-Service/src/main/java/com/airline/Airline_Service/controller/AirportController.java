package com.airline.Airline_Service.controller;

import com.airline.Airline_Service.dto.AirportRequestDTO;
import com.airline.Airline_Service.dto.AirportResponseDTO;
import com.airline.Airline_Service.service.AirportService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/airports")
@RequiredArgsConstructor
public class AirportController {

    private final AirportService service;

    @PostMapping
    public AirportResponseDTO create(@RequestBody AirportRequestDTO dto) {
        return service.createAirport(dto);
    }

    @GetMapping("/iata/{code}")
    public AirportResponseDTO getByIata(@PathVariable String code) {
        return service.getAirportByIata(code);
    }

    @GetMapping("/city/{city}")
    public List<AirportResponseDTO> getByCity(@PathVariable String city) {
        return service.getAirportsByCity(city);
    }

    @GetMapping("/country/{country}")
    public List<AirportResponseDTO> getByCountry(@PathVariable String country) {
        return service.getAirportsByCountry(country);
    }

    @GetMapping("/search")
    public List<AirportResponseDTO> search(@RequestParam String keyword) {
        return service.searchAirports(keyword);
    }

    @PutMapping("/{id}")
    public AirportResponseDTO update(@PathVariable Long id, @RequestBody AirportRequestDTO dto) {
        return service.updateAirport(id, dto);
    }
}