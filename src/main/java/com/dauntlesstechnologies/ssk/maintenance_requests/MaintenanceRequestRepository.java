package com.dauntlesstechnologies.ssk.maintenance_requests;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MaintenanceRequestRepository extends JpaRepository<MaintenanceRequest, Long> {
    
    // This ONE method replaces all your countByStatus...() methods
    int countByStatus(Status status);

    // This ONE method replaces all your findAllByStatus...() methods
    List<MaintenanceRequest> findAllByStatus(Status status);



}
