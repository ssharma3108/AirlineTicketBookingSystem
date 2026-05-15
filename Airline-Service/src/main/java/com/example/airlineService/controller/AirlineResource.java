package com.example.airlineService.controller;

import com.example.airlineService.dto.AirlineDTO;
import com.example.airlineService.service.AirlineService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/airlines")
public class AirlineResource {

    private final AirlineService service;

    public AirlineResource(AirlineService service) {
        this.service = service;
    }

    @PostMapping
    public AirlineDTO create(@RequestBody AirlineDTO dto) {
        return service.createAirline(dto);
    }

    @GetMapping("/{id}")
    public AirlineDTO getById(@PathVariable Long id) {
        return service.getAirlineById(id);
    }

    @GetMapping("/iata/{iata}")
    public AirlineDTO getByIata(@PathVariable String iata) {
        return service.getAirlineByIata(iata);
    }

    @GetMapping
    public List<AirlineDTO> getAll() {
        return service.getAllAirlines();
    }

    @PutMapping("/{id}")
    public AirlineDTO update(@PathVariable Long id, @RequestBody AirlineDTO dto) {
        return service.updateAirline(id, dto);
    }

    @PutMapping("/deactivate/{id}")
    public void deactivate(@PathVariable Long id) {
        service.deactivateAirline(id);
    }
}
