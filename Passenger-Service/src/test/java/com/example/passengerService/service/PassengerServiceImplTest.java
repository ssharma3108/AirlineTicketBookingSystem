package com.example.passengerService.service;

import com.example.passengerService.dto.PassengerDTO;
import com.example.passengerService.entity.PassengerInfo;
import com.example.passengerService.repository.PassengerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PassengerServiceImplTest {

    @Mock PassengerRepository repo;
    @InjectMocks PassengerServiceImpl service;

    @Test
    void addPassenger_success() {
        PassengerDTO dto = new PassengerDTO();
        dto.setFirstName("Kartik");
        when(repo.save(any(PassengerInfo.class))).thenAnswer(i -> i.getArgument(0));

        var res = service.addPassenger(dto);
        assertEquals("Kartik", res.getFirstName());
    }
}
