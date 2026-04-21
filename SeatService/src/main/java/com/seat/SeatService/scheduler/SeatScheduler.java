package com.seat.SeatService.scheduler;

import com.seat.SeatService.entity.Seat;
import com.seat.SeatService.entity.SeatStatus;
import com.seat.SeatService.repository.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SeatScheduler {

    private final SeatRepository repo;

    @Scheduled(fixedRate = 60000)
    public void autoReleaseSeats(){

        List<Seat> heldSeats = repo.findByStatus(SeatStatus.HELD);

        for(Seat seat : heldSeats){

            if(seat.getHoldTime().plusMinutes(15)
                    .isBefore(LocalDateTime.now())){

                seat.setStatus(SeatStatus.AVAILABLE);
                seat.setHoldTime(null);

                repo.save(seat);
            }
        }
    }
}