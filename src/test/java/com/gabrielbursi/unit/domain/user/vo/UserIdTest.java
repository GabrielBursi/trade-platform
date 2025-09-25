package com.gabrielbursi.unit.domain.user.vo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.gabrielbursi.domain.user.vo.UserId;

class UserIdTest {

    @Test
    void shouldGenerateNewId() {
        UserId id1 = UserId.newId();
        UserId id2 = UserId.newId();

        assertNotNull(id1.getValue());
        assertNotNull(id2.getValue());
        assertNotEquals(id1, id2);
    }

    @Test
    void shouldCreateFromValidString() {
        String value = "123e4567-e89b-12d3-a456-426614174000";
        UserId id = UserId.of(value);

        assertEquals(value, id.getValue());
        assertEquals(value, id.toString());
    }

    @Test
    void shouldThrowExceptionWhenNullOrBlank() {
        assertThrows(IllegalArgumentException.class, () -> UserId.of(null));
        assertThrows(IllegalArgumentException.class, () -> UserId.of(""));
        assertThrows(IllegalArgumentException.class, () -> UserId.of("   "));
    }

    @Test
    void shouldBeEqualWhenValueIsTheSame() {
        String value = "abc-123";
        UserId id1 = UserId.of(value);
        UserId id2 = UserId.of(value);

        assertEquals(id1, id2);
        assertTrue(id1.sameAs(id2));
    }

    @Test
    void shouldNotBeEqualWhenValueIsDifferent() {
        UserId id1 = UserId.of("abc-123");
        UserId id2 = UserId.of("xyz-789");

        assertNotEquals(id1, id2);
        assertFalse(id1.sameAs(id2));
    }
}
