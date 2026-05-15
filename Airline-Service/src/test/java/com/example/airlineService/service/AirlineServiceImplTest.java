package com.example.airlineService.service;

import com.example.airlineService.dto.AirlineDTO;
import com.example.airlineService.dto.AirportDTO;
import com.example.airlineService.entity.Airline;
import com.example.airlineService.entity.Airport;
import com.example.airlineService.repository.AirlineRepository;
import com.example.airlineService.repository.AirportRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AirlineServiceImplTest {

    @Mock
    private AirlineRepository airlineRepo;

    @Mock
    private AirportRepository airportRepo;

    @InjectMocks
    private AirlineServiceImpl service;

    // ---------- AIRLINE TESTS ----------

    @Test
    void createAirline_success() {
        AirlineDTO dto = new AirlineDTO();
        dto.name = "IndiGo";
        dto.iataCode = "6E";
        dto.icaoCode = "IGO";
        dto.logoUrl = "https://logo.png";
        dto.country = "India";
        dto.contactEmail = "support@indigo.in";
        dto.contactPhone = "9999999999";

        when(airlineRepo.save(any(Airline.class))).thenAnswer(inv -> {
            Airline a = inv.getArgument(0);
            a.setAirlineId(1L);
            return a;
        });

        AirlineDTO result = service.createAirline(dto);

        assertNotNull(result);
        assertEquals(1L, result.airlineId);
        assertEquals("IndiGo", result.name);
        assertEquals("6E", result.iataCode);
        verify(airlineRepo, times(1)).save(any(Airline.class));
    }

    @Test
    void getAirlineById_success() {
        Airline a = new Airline();
        a.setAirlineId(10L);
        a.setName("Air India");
        a.setIataCode("AI");
        a.setIcaoCode("AIC");
        a.setCountry("India");
        a.setActive(true);

        when(airlineRepo.findById(10L)).thenReturn(Optional.of(a));

        AirlineDTO result = service.getAirlineById(10L);

        assertNotNull(result);
        assertEquals(10L, result.airlineId);
        assertEquals("Air India", result.name);
        assertEquals("AI", result.iataCode);
    }

    @Test
    void getAirlineByIata_success() {
        Airline a = new Airline();
        a.setAirlineId(20L);
        a.setName("Akasa");
        a.setIataCode("QP");

        when(airlineRepo.findByIataCode("QP")).thenReturn(Optional.of(a));

        AirlineDTO result = service.getAirlineByIata("QP");

        assertNotNull(result);
        assertEquals(20L, result.airlineId);
        assertEquals("Akasa", result.name);
        assertEquals("QP", result.iataCode);
    }

    @Test
    void getAllAirlines_success() {
        Airline a1 = new Airline();
        a1.setAirlineId(1L);
        a1.setName("IndiGo");
        a1.setIataCode("6E");

        Airline a2 = new Airline();
        a2.setAirlineId(2L);
        a2.setName("Air India");
        a2.setIataCode("AI");

        when(airlineRepo.findAll()).thenReturn(List.of(a1, a2));

        List<AirlineDTO> result = service.getAllAirlines();

        assertEquals(2, result.size());
        assertEquals("IndiGo", result.get(0).name);
        assertEquals("Air India", result.get(1).name);
    }

    @Test
    void updateAirline_success() {
        Airline existing = new Airline();
        existing.setAirlineId(3L);
        existing.setName("Old Name");
        existing.setCountry("Old Country");

        AirlineDTO req = new AirlineDTO();
        req.name = "New Name";
        req.country = "New Country";

        when(airlineRepo.findById(3L)).thenReturn(Optional.of(existing));
        when(airlineRepo.save(any(Airline.class))).thenAnswer(inv -> inv.getArgument(0));

        AirlineDTO result = service.updateAirline(3L, req);

        assertEquals("New Name", result.name);
        assertEquals("New Country", result.country);
        verify(airlineRepo).save(existing);
    }

    @Test
    void deactivateAirline_success() {
        Airline existing = new Airline();
        existing.setAirlineId(4L);
        existing.setActive(true);

        when(airlineRepo.findById(4L)).thenReturn(Optional.of(existing));
        when(airlineRepo.save(any(Airline.class))).thenAnswer(inv -> inv.getArgument(0));

        service.deactivateAirline(4L);

        assertFalse(existing.isActive());
        verify(airlineRepo).save(existing);
    }

    // ---------- AIRPORT TESTS ----------

    @Test
    void createAirport_success() {
        AirportDTO dto = new AirportDTO();
        dto.name = "Delhi Airport";
        dto.iataCode = "DEL";
        dto.icaoCode = "VIDP";
        dto.city = "Delhi";
        dto.country = "India";
        dto.latitude = 28.5562;
        dto.longitude = 77.1000;
        dto.timezone = "Asia/Kolkata";

        when(airportRepo.save(any(Airport.class))).thenAnswer(inv -> {
            Airport a = inv.getArgument(0);
            a.setAirportId(100L);
            return a;
        });

        AirportDTO result = service.createAirport(dto);

        assertNotNull(result);
        assertEquals(100L, result.airportId);
        assertEquals("DEL", result.iataCode);
        verify(airportRepo).save(any(Airport.class));
    }

    @Test
    void getAirportByIata_success() {
        Airport a = new Airport();
        a.setAirportId(101L);
        a.setName("Mumbai Airport");
        a.setIataCode("BOM");
        a.setCity("Mumbai");
        a.setCountry("India");

        when(airportRepo.findByIataCode("BOM")).thenReturn(Optional.of(a));

        AirportDTO result = service.getAirportByIata("BOM");

        assertNotNull(result);
        assertEquals(101L, result.airportId);
        assertEquals("Mumbai Airport", result.name);
        assertEquals("BOM", result.iataCode);
    }

    @Test
    void searchAirports_success() {
        Airport a1 = new Airport();
        a1.setAirportId(1L);
        a1.setCity("Delhi");
        a1.setIataCode("DEL");

        Airport a2 = new Airport();
        a2.setAirportId(2L);
        a2.setCity("New Delhi");
        a2.setIataCode("NDL");

        when(airportRepo.findByCityContainingIgnoreCase("del"))
                .thenReturn(List.of(a1, a2));

        List<AirportDTO> result = service.searchAirports("del");

        assertEquals(2, result.size());
        assertEquals("DEL", result.get(0).iataCode);
        assertEquals("NDL", result.get(1).iataCode);
    }

    @Test
    void getAirportsByCity_success() {
        Airport a = new Airport();
        a.setAirportId(11L);
        a.setCity("Pune");
        a.setIataCode("PNQ");

        when(airportRepo.findByCity("Pune")).thenReturn(List.of(a));

        List<AirportDTO> result = service.getAirportsByCity("Pune");

        assertEquals(1, result.size());
        assertEquals("PNQ", result.get(0).iataCode);
    }

    @Test
    void getAirportsByCountry_success() {
        Airport a1 = new Airport();
        a1.setAirportId(21L);
        a1.setCountry("India");
        a1.setIataCode("DEL");

        Airport a2 = new Airport();
        a2.setAirportId(22L);
        a2.setCountry("India");
        a2.setIataCode("BOM");

        when(airportRepo.findByCountry("India")).thenReturn(List.of(a1, a2));

        List<AirportDTO> result = service.getAirportsByCountry("India");

        assertEquals(2, result.size());
        assertEquals("DEL", result.get(0).iataCode);
        assertEquals("BOM", result.get(1).iataCode);
    }

    @Test
    void updateAirport_success() {
        Airport existing = new Airport();
        existing.setAirportId(30L);
        existing.setCity("Old City");
        existing.setCountry("Old Country");

        AirportDTO req = new AirportDTO();
        req.city = "New City";
        req.country = "New Country";

        when(airportRepo.findById(30L)).thenReturn(Optional.of(existing));
        when(airportRepo.save(any(Airport.class))).thenAnswer(inv -> inv.getArgument(0));

        AirportDTO result = service.updateAirport(30L, req);

        assertEquals("New City", result.city);
        assertEquals("New Country", result.country);
        verify(airportRepo).save(existing);
    }
}
