package com.gabrielbursi.unit.useCases.user.signup;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.gabrielbursi.repository.user.UserRepository;
import com.gabrielbursi.unit.domain.user.TestUserUtils;
import com.gabrielbursi.useCases.user.signup.InputSignup;
import com.gabrielbursi.useCases.user.signup.SignupUseCase;

public class SignupTest {
    @Test
    void shouldReturnUserIdWhenSignupUser() {
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.findByEmail(any())).thenReturn(Optional.empty());
        var signup = new SignupUseCase(userRepository);
        var input = new InputSignup("JUnit", "Mockito", "mockito@email.com", TestUserUtils.createValidPlainPassoword(),
                TestUserUtils.createValidCpf().getValue());
        var output = signup.execute(input);
        assertNotNull(output.userId());
        assertDoesNotThrow(() -> UUID.fromString(output.userId()));
    }
}
