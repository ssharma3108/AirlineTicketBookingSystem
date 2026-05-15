package com.example.seatService.service;

import com.example.seatService.entity.*;
import com.example.seatService.repository.SeatRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SeatServiceImplTest {

    @Mock SeatRepository repo;
    @InjectMocks SeatServiceImpl service;

    @Test
    void holdSeat_available_to_locked() {
        Seat s = new Seat();
        s.setFlightId(1L); s.setSeatNumber("1A"); s.setStatus(SeatStatus.AVAILABLE);
        when(repo.findByFlightIdAndSeatNumberForUpdate(1L, "1A")).thenReturn(Optional.of(s));
        when(repo.save(any())).thenAnswer(i -> i.getArgument(0));

        var res = service.holdSeat(1L, "1A", "7");
        assertEquals(SeatStatus.LOCKED, res.getStatus());
    }

    @Test
    void lockSeat_locked_to_confirmed() {
        Seat s = new Seat();
        s.setFlightId(1L); s.setSeatNumber("1A"); s.setStatus(SeatStatus.LOCKED);
        when(repo.findByFlightIdAndSeatNumberForUpdate(1L, "1A")).thenReturn(Optional.of(s));
        when(repo.save(any())).thenAnswer(i -> i.getArgument(0));

        var res = service.lockSeat(1L, "1A");
        assertEquals(SeatStatus.CONFIRMED, res.getStatus());
    }
}
