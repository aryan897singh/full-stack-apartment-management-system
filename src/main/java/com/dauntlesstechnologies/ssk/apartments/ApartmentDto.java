package com.dauntlesstechnologies.ssk.apartments;

import java.math.BigDecimal;
import java.util.Date;

public record ApartmentDto(
        Long id,
        String flatNumber,
        BigDecimal expectedRent,
        BigDecimal rentAmount,
        BigDecimal maintenanceAmount,
        BigDecimal paidMaintenance,
        BigDecimal paidRent,
        Boolean rentOutstanding,
        Boolean occupied,
        Date lastOccupied,
        Boolean depositCollected
        ) {}
