package com.dauntlesstechnologies.ssk.lease;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

public record CreateLeaseDto (
        Date start,
        Date end,
        String flatNumber,
        Set<Long> tenantIds,
        BigDecimal rentAmount,
        BigDecimal maintenanceAmount,
        BigDecimal depositAmount,
        Boolean isDepositCollected
){
}
