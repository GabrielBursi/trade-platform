package com.gabrielbursi.useCases.user.signup;

import com.gabrielbursi.domain.user.User;
import com.gabrielbursi.repository.user.UserRepository;

public class SignupUseCase {

    private final UserRepository userRepository;

    public SignupUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public OutputSignup execute(InputSignup input) {
        var existingUser = userRepository.findByEmail(input.email());
        if (existingUser.isPresent()) {
            throw new IllegalArgumentException("User already exists");
        }
        var user = User.create(
                input.firstName(),
                input.lastName(),
                input.email(),
                input.cpf(),
                input.password());
        userRepository.save(user);
        return new OutputSignup(user.getId().getValue());
    }
}
