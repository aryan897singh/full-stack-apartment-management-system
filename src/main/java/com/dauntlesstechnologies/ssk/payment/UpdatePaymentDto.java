package com.dauntlesstechnologies.ssk.payment;

import java.math.BigDecimal;
import java.util.Date;

public record UpdatePaymentDto(
        String flatNumber,
        BigDecimal amount,
        PaymentMethod paymentMethod,
        Date paymentDate
) {
}
