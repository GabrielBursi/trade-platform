package com.gabrielbursi.domain.user.vo;

import java.util.regex.Pattern;

import org.mindrot.jbcrypt.BCrypt;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public final class Password {

    public static final int MIN_LENGTH = 8;

    private static final Pattern LOWERCASE = Pattern.compile(".*[a-z].*");
    private static final Pattern UPPERCASE = Pattern.compile(".*[A-Z].*");
    private static final Pattern DIGIT = Pattern.compile(".*\\d.*");

    private final String hash;

    private Password(String hash) {
        if (hash == null || hash.isBlank()) {
            throw new IllegalArgumentException("Password hash cannot be null or empty");
        }
        this.hash = hash;
    }

    /**
     * Creates a Password from a raw (plain text) string.
     * Validates the password strength and encrypts it.
     */
    public static Password fromPlain(String plainPassword) {
        validatePlainPassword(plainPassword);
        String hash = BCrypt.hashpw(plainPassword, BCrypt.gensalt());
        return new Password(hash);
    }

    /**
     * Rebuilds a Password from an existing hash (e.g., loaded from the database).
     */
    public static Password fromHash(String hash) {
        return new Password(hash);
    }

    public String getHash() {
        return hash;
    }

    /**
     * Verifies whether the plain text password matches the stored hash.
     */
    public boolean matches(String plainPassword) {
        return BCrypt.checkpw(plainPassword, this.hash);
    }

    /**
     * @deprecated
     */
    @Override
    public String toString() throws RuntimeException {
        throw new RuntimeException("method toString not available.");
    }

    private static void validatePlainPassword(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }

        if (value.length() < MIN_LENGTH) {
            throw new IllegalArgumentException("Password must be at least " + MIN_LENGTH + " characters long");
        }

        if (!LOWERCASE.matcher(value).matches()) {
            throw new IllegalArgumentException("Password must contain at least one lowercase letter");
        }

        if (!UPPERCASE.matcher(value).matches()) {
            throw new IllegalArgumentException("Password must contain at least one uppercase letter");
        }

        if (!DIGIT.matcher(value).matches()) {
            throw new IllegalArgumentException("Password must contain at least one digit");
        }
    }
}
