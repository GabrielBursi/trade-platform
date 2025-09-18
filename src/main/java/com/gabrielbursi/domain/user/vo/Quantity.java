package com.gabrielbursi.domain.user.vo;

import java.math.BigDecimal;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
@Getter
public final class Quantity {
    private final BigDecimal value;
    private static final int MIN = 0;

    private Quantity(BigDecimal value) {
        if (value == null) {
            throw new IllegalArgumentException("Quantity cannot be null");
        }
        if (isLessThanMin(value)) {
            throw new IllegalArgumentException("Quantity must be greater than " + MIN);
        }
        this.value = value.stripTrailingZeros();
    }

    public static Quantity of(BigDecimal value) {
        return new Quantity(value);
    }

    public static Quantity of(double value) {
        return new Quantity(BigDecimal.valueOf(value));
    }

    public boolean greaterThan(Quantity other) {
        return this.value.compareTo(other.value) > 0;
    }

    public boolean lessThan(Quantity other) {
        return this.value.compareTo(other.value) < 0;
    }

    public Quantity add(Quantity other) {
        return new Quantity(this.value.add(other.value));
    }

    public Quantity subtract(Quantity other) {
        BigDecimal result = this.value.subtract(other.value);
        if (isLessThanMin(result)) {
            throw new IllegalArgumentException("Resulting quantity must be greater than " + MIN);
        }
        return new Quantity(result);
    }

    @Override
    public String toString() {
        return value.toPlainString();
    }

    private boolean isLessThanMin(BigDecimal value) {
        return value.compareTo(BigDecimal.valueOf(MIN)) <= 0;
    }
}
