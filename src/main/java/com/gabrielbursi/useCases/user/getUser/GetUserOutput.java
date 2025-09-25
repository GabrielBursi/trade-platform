package com.gabrielbursi.useCases.user.getUser;

import java.math.BigDecimal;
import java.util.Map;

import lombok.Builder;

@Builder
public record GetUserOutput(
        String userId,
        String name,
        String email,
        String cpf,
        Map<String, BigDecimal> assets) {

}
