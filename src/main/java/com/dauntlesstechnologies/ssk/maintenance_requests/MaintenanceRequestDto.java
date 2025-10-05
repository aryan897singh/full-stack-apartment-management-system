package com.dauntlesstechnologies.ssk.maintenance_requests;

import com.dauntlesstechnologies.ssk.manager.Manager;
import com.dauntlesstechnologies.ssk.tenants.Tenant;
import com.dauntlesstechnologies.ssk.tenants.TenantDto;

import java.util.Date;

public record MaintenanceRequestDto(
        Long id,
        String flatNumber,
        Long managerId,
        MaintenanceType maintenanceType,
        String title,
        String description,
        Status status,
        Date dateSubmitted

) {
}
