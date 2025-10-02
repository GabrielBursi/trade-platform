package com.gabrielbursi.domain.order.vo;

import java.util.UUID;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
@Getter
public class OrderId {
    private final String value;

    private OrderId(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("OrderId cannot be null or empty");
        }
        this.value = value;
    }

    public static OrderId newId() {
        return new OrderId(UUID.randomUUID().toString());
    }

    public static OrderId of(String value) {
        return new OrderId(value);
    }

    public boolean sameAs(OrderId other) {
        return this.value.equals(other.value);
    }

    @Override
    public String toString() {
        return value;
    }
}
