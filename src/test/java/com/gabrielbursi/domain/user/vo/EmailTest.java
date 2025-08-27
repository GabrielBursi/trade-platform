package com.gabrielbursi.domain.user.vo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class EmailTest {
    @Test
    @DisplayName("Deve criar Email válido com valor correto")
    void shouldCreateValidEmail() {
        Email email = new Email("junit@test.com");

        assertThat(email.getValue()).isEqualTo("junit@test.com");
        assertThat(email.toString()).isEqualTo("junit@test.com");
    }

    @Test
    @DisplayName("Deve lançar exceção de regex ao criar Email")
    void shouldThrowExceptionWhenEmailRegexNoMatch() {

        assertThatThrownBy(() -> new Email("junit"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Invalid email format: junit");
        assertThatThrownBy(() -> new Email("junit@"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Invalid email format: junit@");
        assertThatThrownBy(() -> new Email("junit@teste"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Invalid email format: junit@teste");
        assertThatThrownBy(() -> new Email("junit.@.com"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Invalid email format: junit.@.com");
    }

    @Test
    @DisplayName("Deve lançar exceção quando Email for null")
    void shouldThrowExceptionWhenEmailIsNull() {
        assertThatThrownBy(() -> new Email(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Email cannot be null or empty");
    }

    @Test
    @DisplayName("Deve lançar exceção quando Email for vazio")
    void shouldThrowExceptionWhenEmailIsEmpty() {
        assertThatThrownBy(() -> new Email("   "))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Email cannot be null or empty");
    }

    @Test
    @DisplayName("Dois Emails com mesmo valor devem ser iguais (Value Object)")
    void shouldBeEqualWhenValuesAreSame() {
        Email email1 = new Email("junit@test.com");
        Email email2 = new Email("junit@test.com");

        assertEquals(email1, email2);
        assertEquals(email1.hashCode(), email2.hashCode());
    }

    @Test
    @DisplayName("Factory method 'of' deve criar Email válido")
    void shouldCreateEmailUsingFactoryMethod() {
        Email name = Email.of("junit@test.com");

        assertThat(name.getValue()).isEqualTo("junit@test.com");
    }
}
