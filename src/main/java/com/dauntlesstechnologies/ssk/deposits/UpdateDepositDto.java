package com.dauntlesstechnologies.ssk.deposits;

import java.math.BigDecimal;

public record UpdateDepositDto(
        String flatNumber,
        BigDecimal negotiated,
        BigDecimal paid
) {
}
