package com.gabrielbursi.domain.user.vo;

import java.util.UUID;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
@Getter
public final class UserId {
    private final String value;

    private UserId(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("UserId cannot be null or empty");
        }
        this.value = value;
    }

    public static UserId newId() {
        return new UserId(UUID.randomUUID().toString());
    }

    public static UserId of(String value) {
        return new UserId(value);
    }

    public boolean sameAs(UserId other) {
        return this.value.equals(other.value);
    }

    @Override
    public String toString() {
        return value;
    }

}
