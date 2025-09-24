package com.dauntlesstechnologies.ssk.maintenance_requests;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/maintenanceRequests")
public class MaintenanceRequestController {

    private final MaintenanceRequestService maintenanceRequestService;

    public MaintenanceRequestController(MaintenanceRequestService maintenanceRequestService) {
        this.maintenanceRequestService = maintenanceRequestService;
    }

    @GetMapping("/{id}")
    public MaintenanceRequestDto getMaintenanceRequestById(@PathVariable("id") Long id) {
        return maintenanceRequestService.findMaintenanceRequestById(id);
    }

    //Need a Get mapping to return all the maintenance requests to display in tabular form
    @GetMapping(path = "/getAllMaintenanceRequests")
    public List<MaintenanceRequestDto> getAllMaintenanceRequests(){
        return maintenanceRequestService.getAllMaintenanceRequests();
    }

    @PutMapping(path = "/update/{id}")
    public void update(@PathVariable Long id, @RequestBody TenantUpdateMaintenanceRequestDto tenantUpdateMaintenanceRequestDto){
        maintenanceRequestService.updateMaintenanceRequest(id, tenantUpdateMaintenanceRequestDto);
    }

    @PostMapping(path = "/create")
    public ResponseEntity<MaintenanceRequest> createNewMaintenanceRequest(@RequestBody TenantUpdateMaintenanceRequestDto tenantUpdateMaintenanceRequestDto){
        MaintenanceRequest maintenanceRequest = maintenanceRequestService.createNewMaintenanceRequest(tenantUpdateMaintenanceRequestDto);
        return new ResponseEntity<>(maintenanceRequest, HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/delete/{id}")
    public void deleteMaintenanceRequest(@PathVariable("id") Long id){
        maintenanceRequestService.deleteMaintenanceRequest(id);
    }



     /*
    C - DONE
    R - DONE (both single and all records)
    U - DONE (with date correctly automatically entered, yay!)
    D
     */



}
