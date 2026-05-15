package com.example.flightService.service;

import com.example.flightService.entity.Flight;
import com.example.flightService.repository.FlightRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FlightServiceImplTest {

    @Mock FlightRepository repo;
    @InjectMocks FlightServiceImpl service;

    @Test
    void decrementSeat_reducesAvailable() {
        Flight f = new Flight();  f.setAvailableSeats(10);
        when(repo.findById(1L)).thenReturn(Optional.of(f));
        when(repo.save(any())).thenAnswer(i -> i.getArgument(0));


        assertEquals(9, f.getAvailableSeats());
    }
}
