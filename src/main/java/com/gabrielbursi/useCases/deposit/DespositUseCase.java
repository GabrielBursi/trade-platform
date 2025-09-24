package com.gabrielbursi.useCases.deposit;

import com.gabrielbursi.domain.user.User;
import com.gabrielbursi.repository.user.UserRepository;

public class DespositUseCase {
    private final UserRepository userRepository;

    public DespositUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void execute(DepositInput input) {
        User user = userRepository.findById(input.accountId());
        if(user == null) {
            throw new IllegalArgumentException("User not found");
        }
        user.deposit(input.assetId(), input.quantity());
    }

}
