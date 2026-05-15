package com.example.airlineService.service;

import com.example.airlineService.dto.*;
import com.example.airlineService.entity.*;
import com.example.airlineService.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AirlineServiceImpl implements AirlineService {

    private final AirlineRepository airlineRepo;
    private final AirportRepository airportRepo;

    public AirlineServiceImpl(AirlineRepository airlineRepo, AirportRepository airportRepo) {
        this.airlineRepo = airlineRepo;
        this.airportRepo = airportRepo;
    }

    //AIRLINE

    public AirlineDTO createAirline(AirlineDTO dto) {
        Airline a = new Airline();
        a.setName(dto.name);
        a.setIataCode(dto.iataCode);
        a.setIcaoCode(dto.icaoCode);
        a.setLogoUrl(dto.logoUrl);
        a.setCountry(dto.country);
        a.setContactEmail(dto.contactEmail);
        a.setContactPhone(dto.contactPhone);

        airlineRepo.save(a);
        dto.airlineId = a.getAirlineId();
        return dto;
    }

    public AirlineDTO getAirlineById(Long id) {
        Airline a = airlineRepo.findById(id).orElseThrow();
        return mapAirline(a);
    }

    public AirlineDTO getAirlineByIata(String iata) {
        Airline a = airlineRepo.findByIataCode(iata).orElseThrow();
        return mapAirline(a);
    }

    public List<AirlineDTO> getAllAirlines() {
        return airlineRepo.findAll().stream().map(this::mapAirline).collect(Collectors.toList());
    }

    public AirlineDTO updateAirline(Long id, AirlineDTO dto) {
        Airline a = airlineRepo.findById(id).orElseThrow();
        a.setName(dto.name);
        a.setCountry(dto.country);
        airlineRepo.save(a);
        return mapAirline(a);
    }

    public void deactivateAirline(Long id) {
        Airline a = airlineRepo.findById(id).orElseThrow();
        a.setActive(false);
        airlineRepo.save(a);
    }

    //AIRPORT

    public AirportDTO createAirport(AirportDTO dto) {
        Airport a = new Airport();
        a.setName(dto.name);
        a.setIataCode(dto.iataCode);
        a.setIcaoCode(dto.icaoCode);
        a.setCity(dto.city);
        a.setCountry(dto.country);
        a.setLatitude(dto.latitude);
        a.setLongitude(dto.longitude);
        a.setTimezone(dto.timezone);

        airportRepo.save(a);
        dto.airportId = a.getAirportId();
        return dto;
    }

    public AirportDTO getAirportByIata(String iata) {
        return mapAirport(airportRepo.findByIataCode(iata).orElseThrow());
    }

    public List<AirportDTO> searchAirports(String keyword) {
        return airportRepo.findByCityContainingIgnoreCase(keyword)
                .stream().map(this::mapAirport).collect(Collectors.toList());
    }

    public List<AirportDTO> getAirportsByCity(String city) {
        return airportRepo.findByCity(city).stream().map(this::mapAirport).toList();
    }

    public List<AirportDTO> getAirportsByCountry(String country) {
        return airportRepo.findByCountry(country).stream().map(this::mapAirport).toList();
    }

    public AirportDTO updateAirport(Long id, AirportDTO dto) {
        Airport a = airportRepo.findById(id).orElseThrow();
        a.setCity(dto.city);
        a.setCountry(dto.country);
        airportRepo.save(a);
        return mapAirport(a);
    }

    //MAPPERS

    private AirlineDTO mapAirline(Airline a) {
        AirlineDTO dto = new AirlineDTO();

        dto.airlineId = a.getAirlineId();
        dto.name = a.getName();
        dto.iataCode = a.getIataCode();
        dto.icaoCode = a.getIcaoCode();
        dto.logoUrl = a.getLogoUrl();
        dto.country = a.getCountry();
        dto.contactEmail = a.getContactEmail();
        dto.contactPhone = a.getContactPhone();
        dto.isActive = a.isActive();

        return dto;
    }

    private AirportDTO mapAirport(Airport a) {
        AirportDTO dto = new AirportDTO();

        dto.airportId = a.getAirportId();
        dto.name = a.getName();
        dto.iataCode = a.getIataCode();
        dto.icaoCode = a.getIcaoCode();
        dto.city = a.getCity();
        dto.country = a.getCountry();
        dto.latitude = a.getLatitude();
        dto.longitude = a.getLongitude();
        dto.timezone = a.getTimezone();

        return dto;
    }
}