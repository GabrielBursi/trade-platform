package com.gabrielbursi.useCases.deposit;

import java.math.BigDecimal;

public record DepositInput(String accountId, String assetId, BigDecimal quantity) {

}
