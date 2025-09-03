package com.gabrielbursi.domain.user;

import com.gabrielbursi.domain.user.vo.Cpf;

public class TestUserUtils {
    public static Cpf createValidCpf() {
        return Cpf.of("08315618075");
    }

    public static String createValidPlainPassoword() {
        return "Senha123";
    }
}
