package com.gabrielbursi.useCases.withdraw;

import com.gabrielbursi.domain.user.User;
import com.gabrielbursi.repository.user.UserRepository;

public class WithdrawUseCase {
    private final UserRepository userRepository;

    public WithdrawUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void execute(WithdrawInput input) {
        User user = userRepository.findById(input.accountId());
        if(user == null) {
            throw new IllegalArgumentException("User not found");
        }
        user.withdraw(input.assetId(), input.quantity());
    }
}
