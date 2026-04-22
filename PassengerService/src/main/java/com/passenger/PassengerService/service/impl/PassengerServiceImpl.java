package com.passenger.PassengerService.service.impl;

import com.passenger.PassengerService.client.*;
import com.passenger.PassengerService.dto.*;
import com.passenger.PassengerService.entity.Passenger;
import com.passenger.PassengerService.exception.InvalidPassengerException;
import com.passenger.PassengerService.exception.PassengerNotFoundException;
import com.passenger.PassengerService.repository.PassengerRepository;
import com.passenger.PassengerService.service.PassengerService;
import com.passenger.PassengerService.util.PassengerValidator;
import com.passenger.PassengerService.util.TicketGenerator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PassengerServiceImpl implements PassengerService {

    private final PassengerRepository repository;
    private final TicketGenerator ticketGenerator;
    private final PassengerValidator validator;

    // 🔥 Keep clients (for future use when services are running)
    private final BookingServiceClient bookingClient;
    private final SeatServiceClient seatClient;

    // 🔥 Toggle for MOCK vs REAL mode
    private static final boolean MOCK_MODE = true;

    // ================= ADD PASSENGER =================
    @Override
    public PassengerResponseDTO addPassenger(PassengerRequestDTO dto) {

        // ✅ Validate input
        validator.validate(dto);

        // ✅ Booking Validation
        if (!MOCK_MODE) {
            BookingResponse booking = bookingClient.getBookingById(dto.getBookingId());

            if (booking == null) {
                throw new InvalidPassengerException("Invalid booking ID");
            }
        } else {
            // 🔥 MOCK VALIDATION
            if (dto.getBookingId() == null || dto.getBookingId() <= 0) {
                throw new InvalidPassengerException("Invalid booking ID");
            }
        }

        // ✅ Create Passenger
        Passenger passenger = new Passenger();
        passenger.setBookingId(dto.getBookingId());
        passenger.setTitle(dto.getTitle());
        passenger.setFirstName(dto.getFirstName());
        passenger.setLastName(dto.getLastName());
        passenger.setDateOfBirth(dto.getDateOfBirth());
        passenger.setGender(dto.getGender());
        passenger.setPassportNumber(dto.getPassportNumber());
        passenger.setNationality(dto.getNationality());
        passenger.setPassportExpiry(dto.getPassportExpiry());
        passenger.setPassengerType(dto.getPassengerType());

        // ✅ Generate Ticket
        passenger.setTicketNumber(ticketGenerator.generateTicketNumber());

        // ✅ Save
        Passenger saved = repository.save(passenger);

        // ✅ Response
        return new PassengerResponseDTO(
                saved.getPassengerId(),
                saved.getFirstName() + " " + saved.getLastName(),
                saved.getTicketNumber(),
                saved.getSeatNumber()
        );
    }

    // ================= GET BY ID =================
    @Override
    public Passenger getPassengerById(Long id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        new PassengerNotFoundException("Passenger not found with ID: " + id));
    }

    // ================= GET BY BOOKING =================
    @Override
    public List<Passenger> getPassengersByBooking(Long bookingId) {
        return repository.findByBookingId(bookingId);
    }

    // ================= UPDATE =================
    @Override
    public Passenger updatePassenger(Long id, PassengerRequestDTO dto) {

        Passenger passenger = getPassengerById(id);

        passenger.setFirstName(dto.getFirstName());
        passenger.setLastName(dto.getLastName());

        return repository.save(passenger);
    }

    // ================= ASSIGN SEAT =================
    @Override
    public Passenger assignSeat(Long passengerId, Long seatId) {

        Passenger passenger = getPassengerById(passengerId);

        if (!MOCK_MODE) {
            // 🔥 REAL CALL
            SeatRequest request = new SeatRequest();
            request.setPassengerId(passengerId);
            request.setSeatId(seatId);

            SeatResponse response = seatClient.assignSeat(request);

            passenger.setSeatId(response.getSeatId());
            passenger.setSeatNumber(response.getSeatNumber());

        } else {
            // 🔥 MOCK MODE
            passenger.setSeatId(seatId);
            passenger.setSeatNumber("A" + seatId);
        }

        return repository.save(passenger);
    }

    // ================= DELETE =================
    @Override
    public void deletePassenger(Long id) {
        repository.deleteById(id);
    }

    // ================= COUNT =================
    @Override
    public long getPassengerCount(Long bookingId) {
        return repository.countByBookingId(bookingId);
    }
}