package com.example.eventplanner.domain;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PersonalCode {
    private String code;

    public PersonalCode(String code) {
        if (code == null || !code.matches("^[A-Za-z0-9]{4}$")) {
            throw new IllegalArgumentException("PersonalCode must be exactly 4 characters long and can only contain A-Z, a-z, 0-9.");
        }
        this.code = code;
    }

}
