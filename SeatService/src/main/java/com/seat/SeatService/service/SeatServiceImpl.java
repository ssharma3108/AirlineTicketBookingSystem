package com.seat.SeatService.service;

import com.seat.SeatService.entity.Seat;
import com.seat.SeatService.entity.SeatClass;
import com.seat.SeatService.entity.SeatStatus;
import com.seat.SeatService.repository.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SeatServiceImpl implements SeatService {

    private final SeatRepository repo;

    public Seat addSeat(Seat seat) {
        seat.setStatus(SeatStatus.AVAILABLE);
        return repo.save(seat);
    }

    public List<Seat> getSeatsByFlight(Long flightId) {
        return repo.findByFlightId(flightId);
    }

    private Double calculateMultiplier(Seat seat) {

        double price = 1.0;

        if(seat.getSeatClass() == SeatClass.BUSINESS) price += 1.0;
        if(seat.getSeatClass() == SeatClass.FIRST) price += 2.0;

        if(seat.isWindow()) price += 0.20;
        if(seat.isAisle()) price += 0.15;
        if(seat.isHasExtraLegroom()) price += 0.30;

        return price;
    }

    public Seat holdSeat(Long seatId) {
        Seat seat = repo.findById(seatId).orElseThrow();

        if(seat.getStatus() != SeatStatus.AVAILABLE)
            throw new RuntimeException("Seat not available");

        seat.setStatus(SeatStatus.HELD);
        seat.setHoldTime(LocalDateTime.now());

        return repo.save(seat);
    }

    public Seat releaseSeat(Long seatId) {
        Seat seat = repo.findById(seatId).orElseThrow();

        seat.setStatus(SeatStatus.AVAILABLE);
        seat.setHoldTime(null);

        return repo.save(seat);
    }

    public Seat confirmSeat(Long seatId) {
        Seat seat = repo.findById(seatId).orElseThrow();

        seat.setStatus(SeatStatus.CONFIRMED);

        return repo.save(seat);
    }

    public void deleteSeats(Long flightId) {
        repo.deleteByFlightId(flightId);
    }

    @Override
    public void generateSeatsForFlight(Long flightId) {

        String[] cols = {"A","B","C","D","E","F"};

        for(int row = 1; row <= 30; row++) {

            for(String col : cols) {

                Seat seat = new Seat();

                seat.setFlightId(flightId);
                seat.setSeatNumber(row + col);
                seat.setSeatRow(row);
                seat.setSeatColumn(col);

                seat.setWindow(col.equals("A") || col.equals("F"));
                seat.setAisle(col.equals("C") || col.equals("D"));

                seat.setHasExtraLegroom(row == 12 || row == 13);

                seat.setStatus(SeatStatus.AVAILABLE);

                if(row <= 5) {
                    seat.setSeatClass(SeatClass.FIRST);
                } else if(row <= 10) {
                    seat.setSeatClass(SeatClass.BUSINESS);
                } else {
                    seat.setSeatClass(SeatClass.ECONOMY);
                }

                seat.setPriceMultiplier(calculateMultiplier(seat));

                repo.save(seat);
            }
        }
    }


}