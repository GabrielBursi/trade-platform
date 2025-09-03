package com.gabrielbursi.useCases.user.signup;

import com.gabrielbursi.domain.user.User;

public class Signup {
    public OutputSignup execute(InputSignup input) {
        var user = User.create(
                input.firstName(),
                input.lastName(),
                input.email(),
                input.cpf(),
                input.password());
        return new OutputSignup(user.getId());
    }
}
