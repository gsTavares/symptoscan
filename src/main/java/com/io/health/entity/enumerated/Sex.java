package com.io.health.entity.enumerated;

import java.util.Arrays;
import java.util.List;

public enum Sex {
    FEMALE(0, "female"),
    MALE(1, "male"),
    OTHER(2, "other");

    private final Integer code;
    private final String description;

    private Sex(Integer code, String description) {
        this.code = code;
        this.description = description;
    }

    public Integer getCode() {
        return this.code;
    }

    public String description() {
        return this.description;
    }

    public static Sex toEnum(Integer code) throws IllegalArgumentException {
        List<Sex> allValues = Arrays.asList(Sex.values()); 
        return allValues.stream().filter(s -> s.getCode().equals(code)).findFirst()
        .orElseThrow(() -> new IllegalArgumentException("Invalid code"));
    }
}

