package com.example.eventplanner.exceptions;

public class EventWithNameNotFoundException extends IllegalArgumentException {
    public EventWithNameNotFoundException(String eventName) {

      super("The event with name " + eventName + " was not found");

    }
}
