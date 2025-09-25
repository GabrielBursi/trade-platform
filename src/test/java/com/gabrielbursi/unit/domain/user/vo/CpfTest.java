package com.gabrielbursi.unit.domain.user.vo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.gabrielbursi.domain.user.vo.Cpf;

class CpfTest {

    @Test
    @DisplayName("Deve criar CPF válido com valor correto")
    void shouldCreateValidCpf() {
        Cpf cpf = new Cpf("52998224725");

        assertThat(cpf.getValue()).isEqualTo("52998224725");
        assertThat(cpf.getFormatted()).isEqualTo("529.982.247-25");
        assertThat(cpf.toString()).isEqualTo("529.982.247-25");
    }

    @Test
    @DisplayName("Deve lançar exceção quando CPF for null")
    void shouldThrowExceptionWhenCpfIsNull() {
        assertThatThrownBy(() -> new Cpf(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("CPF cannot be null or empty");
    }

    @Test
    @DisplayName("Deve lançar exceção quando CPF for vazio")
    void shouldThrowExceptionWhenCpfIsEmpty() {
        assertThatThrownBy(() -> new Cpf("   "))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("CPF cannot be null or empty");
    }

    @Test
    @DisplayName("Deve lançar exceção quando CPF tiver tamanho inválido")
    void shouldThrowExceptionWhenCpfHasInvalidLength() {
        assertThatThrownBy(() -> new Cpf("123"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("CPF must have 11 digits: 123");
    }

    @Test
    @DisplayName("Deve lançar exceção quando CPF for sequência repetida")
    void shouldThrowExceptionWhenCpfIsRepeatedSequence() {
        assertThatThrownBy(() -> new Cpf("11111111111"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Invalid CPF: repeated sequence");
    }

    @Test
    @DisplayName("Deve lançar exceção quando dígitos verificadores forem inválidos")
    void shouldThrowExceptionWhenCpfHasInvalidCheckDigits() {
        assertThatThrownBy(() -> new Cpf("52998224726"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Invalid CPF: check digits are incorrect");
    }

    @Test
    @DisplayName("Dois CPFs com mesmo valor devem ser iguais (Value Object)")
    void shouldBeEqualWhenValuesAreSame() {
        Cpf cpf1 = new Cpf("52998224725");
        Cpf cpf2 = new Cpf("529.982.247-25");

        assertThat(cpf1).isEqualTo(cpf2);
        assertThat(cpf1.hashCode()).isEqualTo(cpf2.hashCode());
    }

    @Test
    @DisplayName("Factory method 'of' deve criar CPF válido")
    void shouldCreateCpfUsingFactoryMethod() {
        Cpf cpf = Cpf.of("52998224725");

        assertThat(cpf.getValue()).isEqualTo("52998224725");
    }
}
