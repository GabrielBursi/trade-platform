package com.gabrielbursi.useCases.withdraw;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.gabrielbursi.domain.user.User;
import com.gabrielbursi.repository.user.UserRepository;

class WithdrawUseCaseTest {

    private UserRepository userRepository;
    private WithdrawUseCase withdrawUseCase;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        withdrawUseCase = new WithdrawUseCase(userRepository);
    }

    @Test
    void shouldWithdrawWhenUserExists() {
        String accountId = "acc123";
        String assetId = "asset456";
        BigDecimal quantity = new BigDecimal("10.0");
        WithdrawInput input = mock(WithdrawInput.class);
        when(input.accountId()).thenReturn(accountId);
        when(input.assetId()).thenReturn(assetId);
        when(input.quantity()).thenReturn(quantity);

        User user = mock(User.class);
        when(userRepository.findById(accountId)).thenReturn(user);

        withdrawUseCase.execute(input);

        verify(userRepository).findById(accountId);
        verify(user).withdraw(assetId, quantity);
    }

    @Test
    void shouldThrowExceptionWhenUserNotFound() {
        String accountId = "acc123";
        WithdrawInput input = mock(WithdrawInput.class);
        when(input.accountId()).thenReturn(accountId);
        when(userRepository.findById(accountId)).thenReturn(null);

        assertThatThrownBy(() -> withdrawUseCase.execute(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("User not found");
    }
}
