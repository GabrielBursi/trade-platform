package com.gabrielbursi.unit.domain.user.vo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.gabrielbursi.domain.user.vo.Password;

class PasswordTest {

    @Test
    @DisplayName("Deve criar Password válido a partir de plain text e validar matches")
    void shouldCreateValidPasswordFromPlain() {
        Password password = Password.fromPlain("StrongPass1");

        assertThat(password.getHash()).isNotBlank();
        assertThat(password.matches("StrongPass1")).isTrue();
        assertThat(password.matches("WrongPass1")).isFalse();
    }

    @Test
    @DisplayName("Deve reconstruir Password a partir de hash existente e validar matches")
    void shouldReconstructPasswordFromHash() {
        Password original = Password.fromPlain("StrongPass1");
        String hash = original.getHash();

        Password reconstructed = Password.fromHash(hash);

        assertThat(reconstructed.getHash()).isEqualTo(hash);
        assertThat(reconstructed.matches("StrongPass1")).isTrue();
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar Password com plain null")
    void shouldThrowExceptionWhenPassworPlainTextdIsNull() {
        assertThatThrownBy(() -> Password.fromPlain(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Password cannot be null or empty");
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar Password com plain vazio")
    void shouldThrowExceptionWhenPasswordPlainTextIsEmpty() {
        assertThatThrownBy(() -> Password.fromPlain("   "))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Password cannot be null or empty");
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar Password com hash null")
    void shouldThrowExceptionWhenPassworHashdIsNull() {
        assertThatThrownBy(() -> Password.fromHash(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Password hash cannot be null or empty");
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar Password com hash vazio")
    void shouldThrowExceptionWhenPasswordHashIsEmpty() {
        assertThatThrownBy(() -> Password.fromHash("   "))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Password hash cannot be null or empty");
    }

    @Test
    @DisplayName("Deve lançar exceção quando Password tiver menos que o mínimo permitido")
    void shouldThrowExceptionWhenPasswordIsTooShort() {
        assertThatThrownBy(() -> Password.fromPlain("Aa1"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Password must be at least " + Password.MIN_LENGTH + " characters long");
    }

    @Test
    @DisplayName("Deve lançar exceção quando Password não tiver letra minúscula")
    void shouldThrowExceptionWhenPasswordHasNoLowercase() {
        assertThatThrownBy(() -> Password.fromPlain("PASSWORD1"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Password must contain at least one lowercase letter");
    }

    @Test
    @DisplayName("Deve lançar exceção quando Password não tiver letra maiúscula")
    void shouldThrowExceptionWhenPasswordHasNoUppercase() {
        assertThatThrownBy(() -> Password.fromPlain("password1"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Password must contain at least one uppercase letter");
    }

    @Test
    @DisplayName("Deve lançar exceção quando Password não tiver dígito")
    void shouldThrowExceptionWhenPasswordHasNoDigit() {
        assertThatThrownBy(() -> Password.fromPlain("Password"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Password must contain at least one digit");
    }

    @Test
    @DisplayName("Deve lançar exceção quando toString for chamado")
    void shouldThrowExceptionWhenToStringIsCalled() {
        Password password = Password.fromPlain("StrongPass1");

        assertThatThrownBy(password::toString)
                .isInstanceOf(RuntimeException.class)
                .hasMessage("method toString not available.");
    }

    @Test
    @DisplayName("Dois Passwords com mesmo hash devem ser iguais (Value Object)")
    void shouldBeEqualWhenHashesAreSame() {
        Password password1 = Password.fromPlain("StrongPass1");
        Password password2 = Password.fromHash(password1.getHash());

        assertThat(password1).isEqualTo(password2);
        assertThat(password1.hashCode()).isEqualTo(password2.hashCode());
    }
}
