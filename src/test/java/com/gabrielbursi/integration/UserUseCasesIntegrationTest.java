package com.gabrielbursi.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.gabrielbursi.domain.shared.AssetIdEnum;
import com.gabrielbursi.repository.user.UserRepositoryFake;
import com.gabrielbursi.unit.domain.user.TestUserUtils;
import com.gabrielbursi.useCases.deposit.DepositInput;
import com.gabrielbursi.useCases.deposit.DespositUseCase;
import com.gabrielbursi.useCases.user.getUser.GetUserInput;
import com.gabrielbursi.useCases.user.getUser.GetUserOutput;
import com.gabrielbursi.useCases.user.getUser.GetUserUseCase;
import com.gabrielbursi.useCases.user.signup.InputSignup;
import com.gabrielbursi.useCases.user.signup.OutputSignup;
import com.gabrielbursi.useCases.user.signup.SignupUseCase;
import com.gabrielbursi.useCases.withdraw.WithdrawInput;
import com.gabrielbursi.useCases.withdraw.WithdrawUseCase;

public class UserUseCasesIntegrationTest {

    private SignupUseCase signupUseCase;
    private GetUserUseCase getUserUseCase;
    private DespositUseCase despositUseCase;
    private WithdrawUseCase withdrawUseCase;
    private UserRepositoryFake userRepository;

    @BeforeEach
    void setUp() {
        userRepository = new UserRepositoryFake();
        signupUseCase = new SignupUseCase(userRepository);
        getUserUseCase = new GetUserUseCase(userRepository);
        despositUseCase = new DespositUseCase(userRepository);
        withdrawUseCase = new WithdrawUseCase(userRepository);
    }

    @Test
    void shouldSignupAndGetUser() {
        InputSignup signupInput = new InputSignup(
                "John",
                "Doe",
                "john.doe@email.com",
                TestUserUtils.createValidPlainPassoword(),
                TestUserUtils.createValidCpf().getValue());

        OutputSignup signupOutput = signupUseCase.execute(signupInput);
        assertNotNull(signupOutput.userId());

        GetUserInput getInput = new GetUserInput(signupOutput.userId());
        GetUserOutput getOutput = getUserUseCase.execute(getInput);

        assertEquals(signupInput.firstName() + " " + signupInput.lastName(), getOutput.name());
        assertEquals(signupInput.email(), getOutput.email());
        assertEquals(signupInput.cpf(), getOutput.cpf());
        assertTrue(getOutput.assets().isEmpty());
    }

    @Test
    void shouldSignupAndDeposit() {
        InputSignup signupInput = new InputSignup(
                "Jane",
                "Doe",
                "jane.doe@email.com",
                TestUserUtils.createValidPlainPassoword(),
                TestUserUtils.createValidCpf().getValue());
        OutputSignup signupOutput = signupUseCase.execute(signupInput);

        DepositInput depositInput = new DepositInput(signupOutput.userId(), AssetIdEnum.BTC.toString(), BigDecimal.TEN);
        despositUseCase.execute(depositInput);

        GetUserInput getInput = new GetUserInput(signupOutput.userId());
        GetUserOutput getOutput = getUserUseCase.execute(getInput);

        assertEquals(1, getOutput.assets().size());
        assertEquals(0, BigDecimal.TEN.compareTo(getOutput.assets().get(AssetIdEnum.BTC)));
    }

    @Test
    void shouldSignupDepositAndWithdraw() {
        InputSignup signupInput = new InputSignup(
                "Jim",
                "Doe",
                "jim.doe@email.com",
                TestUserUtils.createValidPlainPassoword(),
                TestUserUtils.createValidCpf().getValue());
        OutputSignup signupOutput = signupUseCase.execute(signupInput);

        DepositInput depositInput = new DepositInput(signupOutput.userId(), AssetIdEnum.BTC.toString(),
                BigDecimal.valueOf(10));
        despositUseCase.execute(depositInput);

        WithdrawInput withdrawInput = new WithdrawInput(signupOutput.userId(), AssetIdEnum.BTC.toString(),
                BigDecimal.valueOf(3));
        withdrawUseCase.execute(withdrawInput);

        GetUserInput getInput = new GetUserInput(signupOutput.userId());
        GetUserOutput getOutput = getUserUseCase.execute(getInput);

        assertEquals(1, getOutput.assets().size());
        assertEquals(0, BigDecimal.valueOf(7).compareTo(getOutput.assets().get(AssetIdEnum.BTC)));
    }

    @Test
    void shouldNotWithdrawWithInsufficientFunds() {
        InputSignup signupInput = new InputSignup(
                "Jack",
                "Doe",
                "jack.doe@email.com",
                TestUserUtils.createValidPlainPassoword(),
                TestUserUtils.createValidCpf().getValue());
        OutputSignup signupOutput = signupUseCase.execute(signupInput);

        DepositInput depositInput = new DepositInput(signupOutput.userId(), "USD", BigDecimal.valueOf(100));
        despositUseCase.execute(depositInput);

        WithdrawInput withdrawInput = new WithdrawInput(signupOutput.userId(), "USD", BigDecimal.valueOf(150));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            withdrawUseCase.execute(withdrawInput);
        });

        assertEquals("Insufficient balance", exception.getMessage());
    }
}
