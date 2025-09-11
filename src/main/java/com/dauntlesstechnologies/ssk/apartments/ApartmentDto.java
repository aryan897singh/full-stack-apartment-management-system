package com.dauntlesstechnologies.ssk.apartments;

import java.math.BigDecimal;

public record ApartmentDto(
        Long id,
        String flatNumber,
        BigDecimal rentAmount,
        Boolean rentOutstanding
) {}
