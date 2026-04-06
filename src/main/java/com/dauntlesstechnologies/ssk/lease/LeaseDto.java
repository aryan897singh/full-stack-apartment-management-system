package com.dauntlesstechnologies.ssk.lease;

import com.dauntlesstechnologies.ssk.tenants.TenantDto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

public record LeaseDto(
        Date start,
        Date end,
        String flatNumber,

        //Purpose of Set of TenantDtos is for a future lease
        // generator where tenant details are required
        Set<TenantDto> tenantDtos,

        BigDecimal rentAmount,
        BigDecimal maintenanceAmount,
        BigDecimal depositAmount,
        Boolean isDepositCollected,
        Boolean isDepositReturned
) {
}
