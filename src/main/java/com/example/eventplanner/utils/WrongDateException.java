package com.example.eventplanner.utils;

public class WrongDateException extends IllegalArgumentException {
    public WrongDateException(String message) {
        super(message);
    }
}
