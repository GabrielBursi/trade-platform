package com.gabrielbursi.useCases.deposit;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.gabrielbursi.domain.user.User;
import com.gabrielbursi.repository.user.UserRepository;

class DespositUseCaseTest {

    private UserRepository userRepository;
    private DespositUseCase despositUseCase;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        despositUseCase = new DespositUseCase(userRepository);
    }

    @Test
    void shouldDepositWhenUserExists() {

        String accountId = "user-1";
        String assetId = "asset-1";
        BigDecimal quantity = BigDecimal.TEN;
        DepositInput input = mock(DepositInput.class);
        when(input.accountId()).thenReturn(accountId);
        when(input.assetId()).thenReturn(assetId);
        when(input.quantity()).thenReturn(quantity);

        User user = mock(User.class);
        when(userRepository.findById(accountId)).thenReturn(user);

        despositUseCase.execute(input);

        verify(userRepository).findById(accountId);
        verify(user).deposit(assetId, quantity);
    }

    @Test
    void shouldThrowExceptionWhenUserNotFound() {

        String accountId = "user-2";
        DepositInput input = mock(DepositInput.class);
        when(input.accountId()).thenReturn(accountId);

        when(userRepository.findById(accountId)).thenReturn(null);

        assertThatThrownBy(() -> despositUseCase.execute(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("User not found");
    }
}