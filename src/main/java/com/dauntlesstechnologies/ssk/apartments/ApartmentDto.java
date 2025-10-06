package com.dauntlesstechnologies.ssk.apartments;

import java.math.BigDecimal;
import java.util.Date;

public record ApartmentDto(
        Long id,
        String flatNumber,
        BigDecimal rentAmount,
        Boolean rentOutstanding,
        Boolean occupied,
        Date lastOccupied) {}
