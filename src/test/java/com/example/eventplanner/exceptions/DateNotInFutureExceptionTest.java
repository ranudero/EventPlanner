package com.example.eventplanner.exceptions;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("unit-tests")
class DateNotInFutureExceptionTest {
    @Test
    void testDateNotInFutureException() {
        DateNotInFutureException dateNotInFutureException = new DateNotInFutureException();
        assertEquals("Start date should be at least tomorrow or further in the future.", dateNotInFutureException.getMessage());
    }

}