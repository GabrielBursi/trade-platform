package com.gabrielbursi.useCases.user.signup;

public record InputSignup(
        String firstName,
        String lastName,
        String email,
        String password,
        String cpf) {

}
