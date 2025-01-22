package com.example.eventplanner.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CustomDateTimeFormatter {
    public static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static String formatToDate(LocalDateTime date) {
        return date.format(DATE_FORMAT);
    }

    public static LocalDateTime parseToDateTime(String date) {
        return LocalDateTime.parse(date, DATE_FORMAT);
    }
}
