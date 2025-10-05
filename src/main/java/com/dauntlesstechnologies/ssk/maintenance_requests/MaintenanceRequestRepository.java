package com.dauntlesstechnologies.ssk.maintenance_requests;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MaintenanceRequestRepository extends JpaRepository<MaintenanceRequest, Long> {

//    //should return the number of open requests
//    public int countByStatusIN_PROGRESS();
//
//    //should return the number of completed requests
//    public int countByStatusCOMPLETED();
//
//    //returns the number of cancelled requests
//    public int countByStatusCANCELLED();
//
//    //returns the number of pending requests
//    public int countByStatusPENDING();
//
//    public List<MaintenanceRequest> findAllByStatusIN_PROGRESS();
//
//    public List<MaintenanceRequest> findAllByStatusCOMPLETED();
//
//    public List<MaintenanceRequest> findAllByStatusCANCELLED();
//
//    public List<MaintenanceRequest> findAllByStatusPENDING();

    // This ONE method replaces all your countByStatus...() methods
    int countByStatus(Status status);

    // This ONE method replaces all your findAllByStatus...() methods
    List<MaintenanceRequest> findAllByStatus(Status status);



}
