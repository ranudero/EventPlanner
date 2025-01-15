package com.example.eventplanner.exceptions;

public class DateNotInFutureException extends IllegalArgumentException {
    public DateNotInFutureException() {
        super("Start date should be at least tomorrow or further in the future.");
    }

}
