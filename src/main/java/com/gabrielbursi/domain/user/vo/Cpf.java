package com.gabrielbursi.domain.user.vo;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public final class Cpf {

    private final String value;

    public Cpf(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("CPF cannot be null or empty");
        }

        String digits = value.replaceAll("\\D", "");

        if (digits.length() != 11) {
            throw new IllegalArgumentException("CPF must have 11 digits: " + value);
        }

        if (isRepeatedSequence(digits)) {
            throw new IllegalArgumentException("Invalid CPF: repeated sequence");
        }

        if (!isValidCpf(digits)) {
            throw new IllegalArgumentException("Invalid CPF: check digits are incorrect");
        }

        this.value = digits;
    }

    public static Cpf of(String value) {
        return new Cpf(value);
    }

    public String getValue() {
        return value;
    }

    public String getFormatted() {
        return value.substring(0, 3) + "." +
                value.substring(3, 6) + "." +
                value.substring(6, 9) + "-" +
                value.substring(9, 11);
    }

    @Override
    public String toString() {
        return getFormatted();
    }

    private static boolean isRepeatedSequence(String digits) {
        return digits.chars().distinct().count() == 1;
    }

    private static boolean isValidCpf(String digits) {
        int firstCheckDigit = calculateCheckDigit(digits, 10);
        int secondCheckDigit = calculateCheckDigit(digits, 11);

        return firstCheckDigit == Character.getNumericValue(digits.charAt(9)) &&
                secondCheckDigit == Character.getNumericValue(digits.charAt(10));
    }

    private static int calculateCheckDigit(String digits, int weightStart) {
        int sum = 0;
        for (int i = 0; i < weightStart - 1; i++) {
            sum += Character.getNumericValue(digits.charAt(i)) * (weightStart - i);
        }
        int remainder = sum % 11;
        return (remainder < 2) ? 0 : 11 - remainder;
    }
}
