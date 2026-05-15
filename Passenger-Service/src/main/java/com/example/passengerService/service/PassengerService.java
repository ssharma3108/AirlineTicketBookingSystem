package com.example.passengerService.service;

import com.example.passengerService.dto.PassengerDTO;
import java.util.List;
import java.util.UUID;

public interface PassengerService {

    PassengerDTO addPassenger(PassengerDTO dto);

    PassengerDTO getPassengerById(Long passengerId);

    List<PassengerDTO> getPassengersByBooking(UUID bookingId);

    PassengerDTO getByPassportNumber(String passportNumber);

    PassengerDTO updatePassenger(Long passengerId, PassengerDTO dto);

    PassengerDTO assignSeat(Long passengerId, Long seatId, String seatNumber);

    String generateTicketNumber();

    long getPassengerCount(UUID bookingId);

    void deletePassenger(Long passengerId);
}