package com.airline.Airline_Service.controller;

import com.airline.Airline_Service.dto.AirlineRequestDTO;
import com.airline.Airline_Service.dto.AirlineResponseDTO;
import com.airline.Airline_Service.service.AirlineService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/airlines")
@RequiredArgsConstructor
public class AirlineController {

    private final AirlineService service;

    @PostMapping
    public AirlineResponseDTO create(@RequestBody AirlineRequestDTO dto) {
        return service.createAirline(dto);
    }

    @GetMapping("/{id}")
    public AirlineResponseDTO getById(@PathVariable Long id) {
        return service.getAirlineById(id);
    }

    @GetMapping("/iata/{code}")
    public AirlineResponseDTO getByIata(@PathVariable String code) {
        return service.getAirlineByIata(code);
    }

    @GetMapping
    public List<AirlineResponseDTO> getAll() {
        return service.getAllAirlines();
    }

    @PutMapping("/{id}")
    public AirlineResponseDTO update(@PathVariable Long id, @RequestBody AirlineRequestDTO dto) {
        return service.updateAirline(id, dto);
    }

    @PutMapping("/{id}/deactivate")
    public String deactivate(@PathVariable Long id) {
        service.deactivateAirline(id);
        return "Airline deactivated successfully";
    }
}