package com.dauntlesstechnologies.ssk.payment;

import com.dauntlesstechnologies.ssk.apartments.Apartment;

import java.math.BigDecimal;
import java.util.Date;

public record PaymentDto(

        Long id,
        Long apartmentId,
        BigDecimal amount,
        PaymentMethod paymentMethod,
        Date paymentDate
) {
}
