package com.gabrielbursi.useCases.user.getUser;

import java.math.BigDecimal;
import java.util.Map;
import java.util.stream.Collectors;

import com.gabrielbursi.domain.user.User;
import com.gabrielbursi.repository.user.UserRepository;

public class GetUserUseCase {
    private final UserRepository userRepository;

    public GetUserUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public GetUserOutput execute(GetUserInput input) {
        User user = userRepository.findById(input.userId());
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        Map<String, BigDecimal> assets = user.getAssets().entrySet().stream()
                .collect(Collectors.toMap(
                        entry -> entry.getKey().getValue(),
                        entry -> entry.getValue().getQuantity().getValue()));

        return GetUserOutput.builder()
                .userId(user.getId().getValue())
                .name(user.getFullName())
                .email(user.getEmail().getValue())
                .cpf(user.getCpf().getValue())
                .assets(assets)
                .build();
    }
}
