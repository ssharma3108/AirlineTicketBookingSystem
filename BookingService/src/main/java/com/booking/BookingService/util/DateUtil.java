package com.booking.BookingService.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtil {

    public static String formatNow() {

        return LocalDateTime.now()
                .format(
                        DateTimeFormatter.ofPattern(
                                "dd-MM-yyyy HH:mm:ss"
                        )
                );
    }

    public static boolean isFuture(
            LocalDateTime dateTime) {

        return dateTime.isAfter(
                LocalDateTime.now()
        );
    }
}