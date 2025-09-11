package com.dauntlesstechnologies.ssk.apartments;

import java.math.BigDecimal;

public record UpdateApartmentDto(
        String flatNumber,
        BigDecimal rentAmount,
        Boolean rentOutstanding
) {
}
