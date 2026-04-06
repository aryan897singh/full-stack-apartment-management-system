package com.dauntlesstechnologies.ssk.payment;

import com.dauntlesstechnologies.ssk.apartments.Apartment;

import java.math.BigDecimal;
import java.util.Date;

public record PaymentDto(
        Long id,
        String flatNumber,
        PaymentType paymentType,
        BigDecimal paymentAmount,
        PaymentMethod paymentMethod,
        String comment,
        Date paymentDate
) {
}
