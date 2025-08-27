package com.gabrielbursi.domain.user.vo;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public final class Name {

    public final static int MIN_LEGTH = 2;
    public final static int MAX_LEGTH = 100;
    private final String value;

    public Name(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        if (value.length() < MIN_LEGTH || value.length() > MAX_LEGTH) {
            throw new IllegalArgumentException(
                    "Name must be between " + MIN_LEGTH + " and " + MAX_LEGTH + " characters");
        }
        this.value = value.trim();
    }

    public static Name of(String value) {
        return new Name(value);
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}
