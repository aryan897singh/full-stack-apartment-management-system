package com.dauntlesstechnologies.ssk.maintenance_requests;

import java.util.Date;

public record TenantUpdateMaintenanceRequestDto(
        MaintenanceType maintenanceType,
        String title,
        String description,
        Date dateSubmitted

) {
}
