package com.dauntlesstechnologies.ssk.lease;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

public record LeaseDto(
        Long id,
        Boolean isActive,
        Date start,
        Date end,
        Long apartmentId,
        Set<Long> tenantIds,
        BigDecimal rentAmount,
        BigDecimal maintenanceAmount,
        BigDecimal depositAmount,
        Boolean isDepositCollected,
        Boolean isDepositReturned
) {
}
