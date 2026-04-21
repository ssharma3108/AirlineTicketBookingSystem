package com.booking.BookingService.util;

import java.util.Random;

public class PnrGenerator {

    private static final String CHARS =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public static String generatePnr() {

        StringBuilder sb = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < 6; i++) {
            sb.append(
                    CHARS.charAt(
                            random.nextInt(CHARS.length())
                    )
            );
        }

        return sb.toString();
    }
}