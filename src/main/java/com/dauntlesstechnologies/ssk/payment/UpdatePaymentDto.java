package com.dauntlesstechnologies.ssk.payment;

import java.math.BigDecimal;
import java.util.Date;

public record UpdatePaymentDto(
        String flatNumber,
        BigDecimal rentAmount,
        BigDecimal maintenanceAmount,
        BigDecimal electricityAmount,
        PaymentMethod paymentMethod,
        Date paymentDate
) {
}
