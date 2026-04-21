package com.booking.BookingService.util;


import com.booking.BookingService.dto.FareResponse;

public class FareCalculator {

    public static FareResponse calculate(
            double baseFare,
            int luggageKg) {

        double taxes = baseFare * 0.10;
        double luggageCharge = luggageKg * 100.0;

        double total =
                baseFare +
                        taxes +
                        luggageCharge;

        return new FareResponse(
                baseFare,
                taxes,
                luggageCharge,
                total
        );
    }
}