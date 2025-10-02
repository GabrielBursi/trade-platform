package com.gabrielbursi.unit.domain.order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.gabrielbursi.domain.order.vo.OrderId;

public class OrderIdTest {
    @Test
    void shouldGenerateNewId() {
        OrderId id1 = OrderId.newId();
        OrderId id2 = OrderId.newId();

        assertNotNull(id1.getValue());
        assertNotNull(id2.getValue());
        assertNotEquals(id1, id2);
    }

    @Test
    void shouldCreateFromValidString() {
        String value = "123e4567-e89b-12d3-a456-426614174000";
        OrderId id = OrderId.of(value);

        assertEquals(value, id.getValue());
        assertEquals(value, id.toString());
    }

    @Test
    void shouldThrowExceptionWhenNullOrBlank() {
        assertThrows(IllegalArgumentException.class, () -> OrderId.of(null));
        assertThrows(IllegalArgumentException.class, () -> OrderId.of(""));
        assertThrows(IllegalArgumentException.class, () -> OrderId.of("   "));
    }

    @Test
    void shouldBeEqualWhenValueIsTheSame() {
        String value = "abc-123";
        OrderId id1 = OrderId.of(value);
        OrderId id2 = OrderId.of(value);

        assertEquals(id1, id2);
        assertTrue(id1.sameAs(id2));
    }

    @Test
    void shouldNotBeEqualWhenValueIsDifferent() {
        OrderId id1 = OrderId.of("abc-123");
        OrderId id2 = OrderId.of("xyz-789");

        assertNotEquals(id1, id2);
        assertFalse(id1.sameAs(id2));
    }
}
