package com.gabrielbursi.useCases.user.signup;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.gabrielbursi.domain.user.TestUserUtils;

public class SignupTest {
    @Test
    void shouldReturnUserIdWhenSignupUser() {
        var signup = new SignupUseCase();
        var input = new InputSignup("JUnit", "Mockito", "mockito@email.com", TestUserUtils.createValidPlainPassoword(),
                TestUserUtils.createValidCpf().getValue());
        var output = signup.execute(input);
        assertNotNull(output.userId());
        assertDoesNotThrow(() -> UUID.fromString(output.userId()));
    }
}
