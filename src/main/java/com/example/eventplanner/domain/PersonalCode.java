package com.example.eventplanner.domain;

import lombok.Getter;

@Getter
public class PersonalCode {
    private String code;

    public PersonalCode(String code) {
        if (code == null || !code.matches("^[A-Za-z0-9]{4}$")) {
            throw new IllegalArgumentException("PersonalCode must be exactly 4 characters long and can only contain A-Z, a-z, 0-9.");
        }
        this.code = code;
    }

}
