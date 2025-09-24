package com.gabrielbursi.useCases.withdraw;

import java.math.BigDecimal;

public record WithdrawInput(String accountId, String assetId, BigDecimal quantity) {

}
