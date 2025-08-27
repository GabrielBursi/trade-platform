package com.gabrielbursi.domain.user.vo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class NameTest {

    @Test
    @DisplayName("Deve criar Name válido com valor correto")
    void shouldCreateValidName() {
        Name name = new Name("Gabriel");

        assertThat(name.getValue()).isEqualTo("Gabriel");
        assertThat(name.toString()).isEqualTo("Gabriel");
    }

    @Test
    @DisplayName("Deve remover espaços extras ao criar Name")
    void shouldTrimWhitespaceWhenCreatingName() {
        Name name = new Name("  Gabriel  ");

        assertThat(name.getValue()).isEqualTo("Gabriel");
    }

    @Test
    @DisplayName("Deve lançar exceção quando Name for null")
    void shouldThrowExceptionWhenNameIsNull() {
        assertThatThrownBy(() -> new Name(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Name cannot be null or empty");
    }

    @Test
    @DisplayName("Deve lançar exceção quando Name for vazio")
    void shouldThrowExceptionWhenNameIsEmpty() {
        assertThatThrownBy(() -> new Name("   "))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Name cannot be null or empty");
    }

    @Test
    @DisplayName("Deve lançar exceção quando Name for menor que mínimo permitido")
    void shouldThrowExceptionWhenNameIsTooShort() {
        assertThatThrownBy(() -> new Name("A"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Name must be between " + Name.MIN_LEGTH + " and " + Name.MAX_LEGTH + " characters");
    }

    @Test
    @DisplayName("Deve lançar exceção quando Name for maior que máximo permitido")
    void shouldThrowExceptionWhenNameIsTooLong() {
        String longName = "A".repeat(Name.MAX_LEGTH + 1);

        assertThatThrownBy(() -> new Name(longName))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Name must be between " + Name.MIN_LEGTH + " and " + Name.MAX_LEGTH + " characters");
    }

    @Test
    @DisplayName("Dois Names com mesmo valor devem ser iguais (Value Object)")
    void shouldBeEqualWhenValuesAreSame() {
        Name name1 = new Name("Gabriel");
        Name name2 = new Name("Gabriel");

        assertThat(name1).isEqualTo(name2);
        assertThat(name1.hashCode()).isEqualTo(name2.hashCode());
    }

    @Test
    @DisplayName("Factory method 'of' deve criar Name válido")
    void shouldCreateNameUsingFactoryMethod() {
        Name name = Name.of("Gabriel");

        assertThat(name.getValue()).isEqualTo("Gabriel");
    }
}
