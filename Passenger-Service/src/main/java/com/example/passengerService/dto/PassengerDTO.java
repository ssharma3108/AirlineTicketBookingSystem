package com.example.passengerService.dto;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

public class PassengerDTO {

	private Long passengerId;

    @NotNull(message = "Booking ID is required")
    private UUID bookingId;

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @NotNull(message = "Date of birth is required")
    @Past(message = "Date of birth must be in the past")
    private LocalDate dateOfBirth;

    @NotBlank(message = "Gender is required")
    private String gender;

    @NotBlank(message = "Passport number is required")
    @Size(min = 6, max = 9, message = "Passport number must be between 6-9 characters")
    private String passportNumber;

    @NotNull(message = "Passport expiry is required")
    @Future(message = "Passport expiry must be in future")
    private LocalDate passportExpiry;

    @NotBlank(message = "Nationality is required")
    private String nationality;

    private Long seatId;
    private String seatNumber;

    private String ticketNumber;
    private String passengerType;

    //GETTERS

    public Long getPassengerId() {
        return passengerId;
    }

    public UUID getBookingId() {
        return bookingId;
    }

    public String getTitle() {
        return title;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public LocalDate getPassportExpiry() {
        return passportExpiry;
    }

    public String getNationality() {
        return nationality;
    }

    public Long getSeatId() {
        return seatId;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public String getTicketNumber() {
        return ticketNumber;
    }

    public String getPassengerType() {
        return passengerType;
    }

    //SETTERS

    public void setPassengerId(Long passengerId) {
        this.passengerId = passengerId;
    }

    public void setBookingId(UUID bookingId) {
        this.bookingId = bookingId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    public void setPassportExpiry(LocalDate passportExpiry) {
        this.passportExpiry = passportExpiry;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public void setSeatId(Long seatId) {
        this.seatId = seatId;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public void setTicketNumber(String ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public void setPassengerType(String passengerType) {
        this.passengerType = passengerType;
    }
}