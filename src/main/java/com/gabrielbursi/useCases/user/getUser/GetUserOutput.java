package com.gabrielbursi.useCases.user.getUser;

import java.math.BigDecimal;
import java.util.Map;

import com.gabrielbursi.domain.shared.AssetIdEnum;

import lombok.Builder;

@Builder
public record GetUserOutput(
        String userId,
        String name,
        String email,
        String cpf,
        Map<AssetIdEnum, BigDecimal> assets) {

}
