package com.dauntlesstechnologies.ssk.deposits;

import java.math.BigDecimal;

public record DepositDto(
        Long id,
        String flatNumber,
        BigDecimal expected,
        BigDecimal negotiated,
        BigDecimal paid
) {
}
