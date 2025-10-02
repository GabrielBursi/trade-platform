package com.gabrielbursi.unit.useCases.user.getUser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.gabrielbursi.domain.shared.AssetId;
import com.gabrielbursi.domain.shared.AssetIdEnum;
import com.gabrielbursi.domain.user.User;
import com.gabrielbursi.domain.user.UserAsset;
import com.gabrielbursi.domain.user.vo.Cpf;
import com.gabrielbursi.domain.user.vo.Email;
import com.gabrielbursi.domain.user.vo.Name;
import com.gabrielbursi.domain.user.vo.Password;
import com.gabrielbursi.domain.user.vo.UserId;
import com.gabrielbursi.repository.user.UserRepository;
import com.gabrielbursi.unit.domain.user.TestUserUtils;
import com.gabrielbursi.useCases.user.getUser.GetUserInput;
import com.gabrielbursi.useCases.user.getUser.GetUserOutput;
import com.gabrielbursi.useCases.user.getUser.GetUserUseCase;

class GetUserUseCaseTest {

    private UserRepository userRepository;
    private GetUserUseCase getUserUseCase;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        getUserUseCase = new GetUserUseCase(userRepository);
    }

    @Test
    void shouldGetUserWhenUserExists() {
        UserId userId = UserId.newId();
        User user = User.builder()
                .id(userId)
                .firstName(Name.of("Gabriel"))
                .lastName(Name.of("Bursi"))
                .email(Email.of("gabriel@email.com"))
                .cpf(Cpf.of(TestUserUtils.createValidCpf().getValue()))
                .password(Password.fromPlain(TestUserUtils.createValidPlainPassoword()))
                .assets(Map.of(
                        AssetId.btc(), UserAsset.create(userId, AssetId.btc().toString(), BigDecimal.TEN),
                        AssetId.usd(), UserAsset.create(userId, AssetId.usd().toString(), BigDecimal.valueOf(100))))
                .build();

        when(userRepository.findById(userId.getValue())).thenReturn(Optional.of(user));

        GetUserInput input = new GetUserInput(userId.getValue());

        GetUserOutput output = getUserUseCase.execute(input);

        assertNotNull(output);
        assertEquals(userId.getValue(), output.userId());
        assertEquals("Gabriel Bursi", output.name());
        assertEquals("gabriel@email.com", output.email());
        assertEquals(TestUserUtils.createValidCpf().getValue(), output.cpf());
        assertEquals(2, output.assets().size());
        assertEquals(0, BigDecimal.TEN.compareTo(output.assets().get(AssetIdEnum.BTC)));
        assertEquals(0, BigDecimal.valueOf(100).compareTo(output.assets().get(AssetIdEnum.USD)));
    }

    @Test
    void shouldThrowExceptionWhenUserNotFound() {
        String userId = "non-existent-user";
        when(userRepository.findById(userId)).thenReturn(Optional.empty());
        GetUserInput input = new GetUserInput(userId);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> getUserUseCase.execute(input));
        assertEquals("User not found", exception.getMessage());
    }

    @Test
    void shouldReturnUserWithEmptyAssetsWhenUserHasNoAssets() {
        UserId userId = UserId.newId();
        User user = User.builder()
                .id(userId)
                .firstName(Name.of("Gabriel"))
                .lastName(Name.of("Bursi"))
                .email(Email.of("gabriel@email.com"))
                .cpf(Cpf.of(TestUserUtils.createValidCpf().getValue()))
                .password(Password.fromPlain(TestUserUtils.createValidPlainPassoword()))
                .assets(Collections.emptyMap())
                .build();

        when(userRepository.findById(userId.getValue())).thenReturn(Optional.of(user));

        GetUserInput input = new GetUserInput(userId.getValue());

        GetUserOutput output = getUserUseCase.execute(input);

        assertNotNull(output);
        assertEquals(userId.getValue(), output.userId());
        assertNotNull(output.assets());
        assertTrue(output.assets().isEmpty());
    }
}
