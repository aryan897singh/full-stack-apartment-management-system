package com.dauntlesstechnologies.ssk.maintenance_requests;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/maintenanceRequests")
public class MaintenanceRequestController {

    private final MaintenanceRequestService maintenanceRequestService;

    public MaintenanceRequestController(MaintenanceRequestService maintenanceRequestService) {
        this.maintenanceRequestService = maintenanceRequestService;
    }

    /*
    C
    R - DONE
    U
    D
     */

    @GetMapping("/{id}")
    public MaintenanceRequestDto getMaintenanceRequestById(@PathVariable("id") Long id) {
        return maintenanceRequestService.findMaintenanceRequestById(id);
    }


}
