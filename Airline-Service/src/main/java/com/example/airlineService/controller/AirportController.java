package com.example.airlineService.controller;

import com.example.airlineService.dto.AirportDTO;
import com.example.airlineService.service.AirlineService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/airports")
public class AirportController {

    private final AirlineService service;

    public AirportController(AirlineService service) {
        this.service = service;
    }

    //Create Airport
    @PostMapping
    public AirportDTO createAirport(@RequestBody AirportDTO dto) {
        return service.createAirport(dto);
    }

    //Get Airport by IATA
    @GetMapping("/iata/{iata}")
    public AirportDTO getByIata(@PathVariable String iata) {
        return service.getAirportByIata(iata);
    }

    //Search Airports (autocomplete use-case)
    @GetMapping("/search")
    public List<AirportDTO> search(@RequestParam String keyword) {
        return service.searchAirports(keyword);
    }

    //Get by City
    @GetMapping("/city/{city}")
    public List<AirportDTO> getByCity(@PathVariable String city) {
        return service.getAirportsByCity(city);
    }

    //Get by Country
    @GetMapping("/country/{country}")
    public List<AirportDTO> getByCountry(@PathVariable String country) {
        return service.getAirportsByCountry(country);
    }

    //Update Airport
    @PutMapping("/{id}")
    public AirportDTO updateAirport(@PathVariable Long id,
                                    @RequestBody AirportDTO dto) {
        return service.updateAirport(id, dto);
    }

    //Get All Airports (needed by admin dashboard/table)
    @GetMapping
    public List<AirportDTO> getAllAirports() {
        return service.searchAirports("");
    }


}