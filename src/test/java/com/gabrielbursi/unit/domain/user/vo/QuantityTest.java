package com.gabrielbursi.unit.domain.user.vo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import com.gabrielbursi.domain.user.vo.Quantity;

class QuantityTest {

    @Test
    void shouldCreateQuantityWithValidBigDecimal() {
        Quantity q = Quantity.of(BigDecimal.valueOf(10));
        assertEquals(0, q.getValue().compareTo(BigDecimal.TEN));
        assertEquals("10", q.toString());
    }

    @Test
    void shouldCreateQuantityWithValidDouble() {
        Quantity q = Quantity.of(5.5);
        assertEquals("5.5", q.toString());
        assertEquals(BigDecimal.valueOf(5.5), q.getValue());
    }

    @Test
    void shouldThrowExceptionWhenValueIsNull() {
        assertThrows(IllegalArgumentException.class, () -> Quantity.of((BigDecimal) null));
    }

    @Test
    void shouldThrowExceptionWhenValueIsZero() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> Quantity.of(0));
        assertEquals("Quantity must be greater than 0", ex.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenValueIsNegative() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> Quantity.of(-1));
        assertEquals("Quantity must be greater than 0", ex.getMessage());
    }

    @Test
    void shouldCompareQuantitiesCorrectly() {
        Quantity q1 = Quantity.of(10);
        Quantity q2 = Quantity.of(20);

        assertTrue(q2.greaterThan(q1));
        assertTrue(q1.lessThan(q2));
        assertFalse(q1.greaterThan(q2));
        assertFalse(q2.lessThan(q1));
    }

    @Test
    void shouldAddQuantitiesCorrectly() {
        Quantity q1 = Quantity.of(10);
        Quantity q2 = Quantity.of(5);

        Quantity result = q1.add(q2);

        assertEquals(Quantity.of(15), result);
    }

    @Test
    void shouldSubtractQuantitiesCorrectly() {
        Quantity q1 = Quantity.of(10);
        Quantity q2 = Quantity.of(5);

        Quantity result = q1.subtract(q2);

        assertEquals(Quantity.of(5), result);
    }

    @Test
    void shouldThrowExceptionWhenSubtractResultsInZeroOrNegative() {
        Quantity q1 = Quantity.of(10);
        Quantity q2 = Quantity.of(10);
        Quantity q3 = Quantity.of(20);

        assertThrows(IllegalArgumentException.class, () -> q1.subtract(q2));
        assertThrows(IllegalArgumentException.class, () -> q1.subtract(q3));
    }

    @Test
    void shouldRespectEqualsAndHashCode() {
        Quantity q1 = Quantity.of(10);
        Quantity q2 = Quantity.of(10);
        Quantity q3 = Quantity.of(20);

        assertEquals(q1, q2);
        assertNotEquals(q1, q3);
        assertEquals(q1.hashCode(), q2.hashCode());
    }
}
