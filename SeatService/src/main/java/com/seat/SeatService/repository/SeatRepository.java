package com.seat.SeatService.repository;

import com.seat.SeatService.entity.Seat;
import com.seat.SeatService.entity.SeatClass;
import com.seat.SeatService.entity.SeatStatus;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.beans.Transient;
import java.util.List;
import java.util.Optional;

public interface SeatRepository extends JpaRepository<Seat, Long> {

    List<Seat> findByFlightId(Long flightId);

    List<Seat> findByFlightIdAndSeatClass(Long flightId, SeatClass seatClass);

    Optional<Seat> findByFlightIdAndSeatNumber(Long flightId, String seatNumber);

    List<Seat> findByFlightIdAndStatus(Long flightId, SeatStatus status);

    List<Seat> findByStatus(SeatStatus status);

    @Transactional
    void deleteByFlightId(Long flightId);
}