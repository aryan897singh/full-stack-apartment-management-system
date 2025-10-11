package com.dauntlesstechnologies.ssk.apartments;

import java.math.BigDecimal;
import java.util.Date;


public record UpdateApartmentDto(
        String flatNumber,
        BigDecimal expectedRent,
        BigDecimal rentAmount,
        BigDecimal maintenanceAmount,
        BigDecimal paidMaintenance,
        BigDecimal paidRent
) {
}
