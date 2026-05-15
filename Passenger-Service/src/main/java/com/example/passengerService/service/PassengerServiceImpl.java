package com.example.passengerService.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.passengerService.client.BookingClient;
import com.example.passengerService.dto.BookingDTO;
import com.example.passengerService.dto.PassengerDTO;
import com.example.passengerService.entity.PassengerInfo;
import com.example.passengerService.entity.PassengerType;
import com.example.passengerService.repository.PassengerRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PassengerServiceImpl implements PassengerService {

    @Autowired
    private PassengerRepository repository;
    
    private final BookingClient bookingClient;
    public PassengerServiceImpl(BookingClient bookingClient) {
    			this.bookingClient = bookingClient;
    }


    // ADD PASSENGER
    @Override
    public PassengerDTO addPassenger(PassengerDTO dto) {

        //Booking fetch 
        BookingDTO booking = bookingClient.getBooking(dto.getBookingId());

        if (booking == null) {
            throw new RuntimeException("Booking not found");
        }

        // Booking status check
        if (!"PENDING".equalsIgnoreCase(booking.getStatus())) {
            throw new RuntimeException("Cannot add passenger, booking not in Pending state");
        }

        //  Seat from booking 
        String seat = booking.getSeatNumber();

        if (seat == null) {
            throw new RuntimeException("Seat not assigned in booking");
        }

        PassengerInfo p = new PassengerInfo();

        // link
        p.setBookingId(dto.getBookingId());

        p.setTitle(dto.getTitle());
        p.setFirstName(dto.getFirstName());
        p.setLastName(dto.getLastName());
        p.setDateOfBirth(dto.getDateOfBirth());
        p.setGender(dto.getGender());
        p.setPassportNumber(dto.getPassportNumber());
        p.setPassportExpiry(dto.getPassportExpiry().toString());
        p.setNationality(dto.getNationality());

        // seat always booking se
        p.setSeatNumber(seat);

        //Ticket number generate
        p.setTicketNumber(generateTicketNumber());

        //Passenger Type calculate
        p.setPassengerType(calculatePassengerType(dto.getDateOfBirth()));

        PassengerInfo saved = repository.save(p);

        return mapToDTO(saved);
    }

    //GET BY ID
    @Override
    public PassengerDTO getPassengerById(Long passengerId) {
        PassengerInfo p = repository.findById(passengerId)
                .orElseThrow(() -> new RuntimeException("Passenger not found"));
        return mapToDTO(p);
    }

    //GET BY BOOKING
    @Override
    public List<PassengerDTO> getPassengersByBooking(UUID bookingId) {
        return repository.findByBookingId(bookingId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    //GET BY PASSPORT
    @Override
    public PassengerDTO getByPassportNumber(String passportNumber) {
        PassengerInfo p = repository.findByPassportNumber(passportNumber)
                .orElseThrow(() -> new RuntimeException("Passenger not found"));
        return mapToDTO(p);
    }

    //UPDATE
    @Override
    public PassengerDTO updatePassenger(Long passengerId, PassengerDTO dto) {
        PassengerInfo p = repository.findById(passengerId)
                .orElseThrow(() -> new RuntimeException("Passenger not found"));

        p.setFirstName(dto.getFirstName());
        p.setLastName(dto.getLastName());
        p.setNationality(dto.getNationality());

        return mapToDTO(repository.save(p));
    }

    //ASSIGN SEAT
    @Override
    public PassengerDTO assignSeat(Long passengerId, Long seatId, String seatNumber) {
        PassengerInfo p = repository.findById(passengerId)
                .orElseThrow(() -> new RuntimeException("Passenger not found"));

        p.setSeatId(seatId);
        p.setSeatNumber(seatNumber);

        return mapToDTO(repository.save(p));
    }

    // COUNT
    @Override
    public long getPassengerCount(UUID bookingId) {
        return repository.countByBookingId(bookingId);
    }

    //DELETE
    @Override
    public void deletePassenger(Long passengerId) {
        repository.deleteById(passengerId);
    }

    // GENERATE TICKET
    @Override
    public String generateTicketNumber() {
        return "TKT-" + UUID.randomUUID().toString().substring(0, 8);
    }

    // PRIVATE METHODS

    private PassengerDTO mapToDTO(PassengerInfo p) {
        PassengerDTO dto = new PassengerDTO();

        dto.setPassengerId(p.getPassengerId());
        dto.setBookingId(p.getBookingId());
        dto.setTitle(p.getTitle());
        dto.setFirstName(p.getFirstName());
        dto.setLastName(p.getLastName());
        dto.setDateOfBirth(p.getDateOfBirth());
        dto.setGender(p.getGender());
        dto.setPassportNumber(p.getPassportNumber());
        dto.setPassportExpiry(LocalDate.parse(p.getPassportExpiry()));
        dto.setNationality(p.getNationality());
        dto.setSeatId(p.getSeatId());
        dto.setSeatNumber(p.getSeatNumber());
        dto.setTicketNumber(p.getTicketNumber());
        dto.setPassengerType(p.getPassengerType().name());

        return dto;
    }

    private PassengerType calculatePassengerType(LocalDate dob) {
        int age = LocalDate.now().getYear() - dob.getYear();

        if (age < 2) return PassengerType.INFANT;
        else if (age < 12) return PassengerType.CHILD;
        else return PassengerType.ADULT;
    }
}