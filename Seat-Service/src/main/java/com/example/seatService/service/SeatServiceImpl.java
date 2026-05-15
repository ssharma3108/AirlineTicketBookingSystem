package com.example.seatService.service;

import com.example.seatService.dto.SeatRequestDTO;
import com.example.seatService.dto.SeatResponseDTO;
import com.example.seatService.entity.Seat;
import com.example.seatService.entity.SeatClass;
import com.example.seatService.entity.SeatStatus;
import com.example.seatService.repository.SeatRepository;
import jakarta.transaction.Transactional;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SeatServiceImpl implements SeatService {

    private static final long LOCK_TIMEOUT_MINUTES = 10;

    private final SeatRepository seatRepository;

    public SeatServiceImpl(SeatRepository seatRepository) {
        this.seatRepository = seatRepository;
    }

    @Override
    public List<SeatResponseDTO> addSeats(List<SeatRequestDTO> seatDTOs) {
        List<Seat> seats = new ArrayList<>();

        for (SeatRequestDTO dto : seatDTOs) {
            Seat seat = new Seat();
            seat.setFlightId(dto.getFlightId());
            seat.setSeatNumber(dto.getSeatNumber());
            seat.setSeatClass(dto.getSeatClass());
            seat.setRowNumber(dto.getRowNumber());
            seat.setColumnLetter(dto.getColumnLetter());
            seat.setWindow(dto.isWindow());
            seat.setAisle(dto.isAisle());
            seat.setHasExtraLegroom(dto.isHasExtraLegroom());
            seat.setPriceMultiplier(dto.getPriceMultiplier());
            seat.setStatus(SeatStatus.AVAILABLE);
            seats.add(seat);
        }

        List<Seat> saved = seatRepository.saveAll(seats);
        return mapToDTOList(saved);
    }

    @Override
    public List<SeatResponseDTO> getSeatsByFlight(Long flightId) {
        releaseExpiredLocks();
        return mapToDTOList(seatRepository.findByFlightId(flightId));
    }

    @Override
    public List<SeatResponseDTO> getAvailableSeats(Long flightId) {
        releaseExpiredLocks();
        return mapToDTOList(seatRepository.findByFlightIdAndStatus(flightId, SeatStatus.AVAILABLE));
    }

    @Override
    public List<SeatResponseDTO> getByClass(Long flightId, SeatClass seatClass) {
        releaseExpiredLocks();
        return mapToDTOList(seatRepository.findByFlightIdAndSeatClass(flightId, seatClass));
    }

    @Override
    @Transactional
    public SeatResponseDTO holdSeat(Long flightId, String seatNumber, String userId) {
        releaseExpiredLocks();

        Seat seat = seatRepository.findByFlightIdAndSeatNumberForUpdate(flightId, seatNumber)
                .orElseThrow(() -> new RuntimeException("Seat not found"));

        if (seat.getStatus() != SeatStatus.AVAILABLE) {
            throw new RuntimeException("Seat not available");
        }

        LocalDateTime now = LocalDateTime.now();
        seat.setStatus(SeatStatus.LOCKED);
        seat.setLockedByUserId(userId);
        seat.setLockedAt(now);
        seat.setLockExpiresAt(now.plusMinutes(LOCK_TIMEOUT_MINUTES));

        return mapToDTO(seatRepository.save(seat));
    }

    @Override
    @Transactional
    public SeatResponseDTO releaseSeat(Long flightId, String seatNumber) {
        Seat seat = seatRepository.findByFlightIdAndSeatNumberForUpdate(flightId, seatNumber)
                .orElseThrow(() -> new RuntimeException("Seat not found"));

        if (seat.getStatus() == SeatStatus.CONFIRMED) {
            throw new RuntimeException("Cannot release booked seat");
        }

        seat.setStatus(SeatStatus.AVAILABLE);
        seat.setLockedByUserId(null);
        seat.setLockedAt(null);
        seat.setLockExpiresAt(null);

        return mapToDTO(seatRepository.save(seat));
    }

    //confirm
    @Override
    @Transactional
    public SeatResponseDTO confirmSeat(Long seatId) {
        Seat seat = getSeatOrThrow(seatId);

        if (seat.getStatus() != SeatStatus.LOCKED) {
            throw new RuntimeException("Seat must be LOCKED before confirmation");
        }

        seat.setStatus(SeatStatus.CONFIRMED);
        seat.setLockedByUserId(null);
        seat.setLockedAt(null);
        seat.setLockExpiresAt(null);

        return mapToDTO(seatRepository.save(seat));
    }


    //lock seat
    @Override
    @Transactional
    public void deleteSeatsByFlight(Long flightId) {
        seatRepository.deleteByFlightId(flightId);
    }

    @Override
    @Transactional
    public SeatResponseDTO lockSeat(Long flightId, String seatNumber) {
        Seat seat = seatRepository.findByFlightIdAndSeatNumberForUpdate(flightId, seatNumber)
                .orElseThrow(() -> new RuntimeException("Seat not found"));

        if (seat.getStatus() != SeatStatus.LOCKED) {
            throw new RuntimeException("Seat must be LOCKED before confirmation");
        }

        seat.setStatus(SeatStatus.CONFIRMED);
        seat.setLockedByUserId(null);
        seat.setLockedAt(null);
        seat.setLockExpiresAt(null);

        return mapToDTO(seatRepository.save(seat));
    }

    //Find expired locked seats and release them.
    @Scheduled(fixedDelay = 30000) //30 sec refresh
    @Transactional
    public void releaseExpiredLocks() {
        LocalDateTime now = LocalDateTime.now();
        List<Seat> expired = seatRepository.findByStatusAndLockExpiresAtBefore(SeatStatus.LOCKED, now);

        if (expired.isEmpty()) return;

        for (Seat seat : expired) {
            seat.setStatus(SeatStatus.AVAILABLE);
            seat.setLockedByUserId(null);
            seat.setLockedAt(null);
            seat.setLockExpiresAt(null);
        }
        seatRepository.saveAll(expired);
    }

    private Seat getSeatOrThrow(Long seatId) {
        Optional<Seat> seat = seatRepository.findById(seatId);
        if (seat.isEmpty()) throw new RuntimeException("Seat not found with id: " + seatId);
        return seat.get();
    }

    private SeatResponseDTO mapToDTO(Seat seat) {
        SeatResponseDTO dto = new SeatResponseDTO();
        dto.setSeatId(seat.getSeatId());
        dto.setFlightId(seat.getFlightId());
        dto.setSeatNumber(seat.getSeatNumber());
        dto.setSeatClass(seat.getSeatClass());
        dto.setRowNumber(seat.getRowNumber());
        dto.setColumnLetter(seat.getColumnLetter());
        dto.setStatus(seat.getStatus());
        return dto;
    }

    private List<SeatResponseDTO> mapToDTOList(List<Seat> seats) {
        List<SeatResponseDTO> list = new ArrayList<>();
        for (Seat seat : seats) list.add(mapToDTO(seat));
        return list;
    }
}
