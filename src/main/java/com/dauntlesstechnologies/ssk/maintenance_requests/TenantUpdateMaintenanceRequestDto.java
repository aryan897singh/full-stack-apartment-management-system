package com.dauntlesstechnologies.ssk.maintenance_requests;

import java.util.Date;

public record TenantUpdateMaintenanceRequestDto(
        Long tenantId,  //this is temporary because I need to study authentication and authorization
        Long ManagerId,
        MaintenanceType maintenanceType,
        String title,
        String description,
        Date dateSubmitted

) {
}
