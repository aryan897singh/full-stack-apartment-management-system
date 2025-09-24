package com.dauntlesstechnologies.ssk.manager;

import com.dauntlesstechnologies.ssk.maintenance_requests.MaintenanceType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface ManagerRepository extends JpaRepository<Manager,Long> {
    Set<Manager> findByMaintenanceTypesContaining(MaintenanceType maintenanceType);
}
