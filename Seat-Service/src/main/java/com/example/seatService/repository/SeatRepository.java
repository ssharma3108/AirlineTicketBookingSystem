package com.example.seatService.repository;

import com.example.seatService.entity.Seat;
import com.example.seatService.entity.SeatClass;
import com.example.seatService.entity.SeatStatus;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface SeatRepository extends JpaRepository<Seat, Long> {

    List<Seat> findByFlightId(Long flightId);

    List<Seat> findByFlightIdAndSeatClass(Long flightId, SeatClass seatClass);

    List<Seat> findByFlightIdAndStatus(Long flightId, SeatStatus status);

    Optional<Seat> findBySeatNumberAndFlightId(String seatNumber, Long flightId);

    Optional<Seat> findByFlightIdAndSeatNumber(Long flightId, String seatNumber);

    long countByFlightIdAndSeatClassAndStatus(Long flightId, SeatClass seatClass, SeatStatus status);

    void deleteByFlightId(Long flightId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select s from Seat s where s.flightId = :flightId and s.seatNumber = :seatNumber")
    Optional<Seat> findByFlightIdAndSeatNumberForUpdate(@Param("flightId") Long flightId,
                                                        @Param("seatNumber") String seatNumber);

    List<Seat> findByStatusAndLockExpiresAtBefore(SeatStatus status, LocalDateTime now);
}
