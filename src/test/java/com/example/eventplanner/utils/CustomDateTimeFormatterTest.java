package com.example.eventplanner.utils;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("unit-tests")
class CustomDateTimeFormatterTest {

    @Test
    void testDateFormatsCorrectlyIntoString() {
        // given
        LocalDateTime givenDate = LocalDateTime.of(2024, 3, 16, 10, 30, 0);
        String expected = "2024-03-16 10:30:00";

        // when
        String result = CustomDateTimeFormatter.formatToDate(givenDate);

        // then
        assertEquals(expected, result, "The date should be formatted as follows: yyyy-MM-dd HH:mm:ss");
    }

    @Test
    void testDateStringParsesCorrectlyIntoDateTime() {
        // given
        String givenDate = "2024-03-16 10:30:00";
        LocalDateTime expected = LocalDateTime.of(2024, 3, 16, 10, 30, 0);

        // when
        LocalDateTime result = CustomDateTimeFormatter.parseToDateTime(givenDate);

        // then
        assertEquals(expected, result, "The date should be parsed as follows: yyyy-MM-dd HH:mm:ss");
    }
}