package com.dauntlesstechnologies.ssk.manager;

import com.dauntlesstechnologies.ssk.maintenance_requests.MaintenanceType;

import java.util.Set;

public record UpdateManagerDto(
        String name,
        Long number,
        Set<MaintenanceType> maintenanceTypes
) {
}
