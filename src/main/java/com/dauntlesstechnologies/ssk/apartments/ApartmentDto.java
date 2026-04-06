package com.dauntlesstechnologies.ssk.apartments;

import java.math.BigDecimal;
import java.util.Date;

public record ApartmentDto(
        String flatNumber,
        Boolean occupied,
        Date lastOccupied
        ) {
}
