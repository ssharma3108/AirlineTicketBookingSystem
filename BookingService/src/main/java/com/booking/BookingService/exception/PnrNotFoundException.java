package com.booking.BookingService.exception;

public class PnrNotFoundException extends RuntimeException {

    public PnrNotFoundException(String message) {
        super(message);
    }
}