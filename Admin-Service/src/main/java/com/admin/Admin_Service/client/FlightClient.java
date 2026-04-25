package com.admin.Admin_Service.client;

import com.admin.Admin_Service.dto.AirlineDTO;
import com.admin.Admin_Service.dto.AirportDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "FLIGHT-SERVICE")
public interface FlightClient {

    @GetMapping("/api/airlines")
    List<AirlineDTO> getAllAirlines();

    @PostMapping("/api/airlines")
    AirlineDTO createAirline(@RequestBody AirlineDTO dto);

    @PutMapping("/api/airlines/{id}/deactivate")
    String deactivateAirline(@PathVariable Long id);

    @GetMapping("/api/airports")
    List<AirportDTO> getAllAirports();

    @PostMapping("/api/airports")
    AirportDTO createAirport(@RequestBody AirportDTO dto);

    @PutMapping("/api/airports/{id}")
    AirportDTO updateAirport(@PathVariable Long id, @RequestBody AirportDTO dto);
}