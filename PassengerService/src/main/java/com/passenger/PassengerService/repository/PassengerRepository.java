package com.passenger.PassengerService.repository;

import com.passenger.PassengerService.entity.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PassengerRepository extends JpaRepository<Passenger, Long> {

    List<Passenger> findByBookingId(Long bookingId);

    Optional<Passenger> findByPassengerId(Long passengerId);

    Optional<Passenger> findByPassportNumber(String passportNumber);

    Optional<Passenger> findByTicketNumber(String ticketNumber);

    Optional<Passenger> findBySeatId(Long seatId);

    long countByBookingId(Long bookingId);

    void deleteByBookingId(Long bookingId);
}
