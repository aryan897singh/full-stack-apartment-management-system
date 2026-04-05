package com.dauntlesstechnologies.ssk.payment;

import java.math.BigDecimal;
import java.util.Date;

public record UpdatePaymentDto(
        String flatNumber,
        PaymentType paymentType,
        BigDecimal paymentAmount,
        PaymentMethod paymentMethod,
        String comment,
        Date paymentDate
) {
}
