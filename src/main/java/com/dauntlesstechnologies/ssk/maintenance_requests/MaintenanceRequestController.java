package com.dauntlesstechnologies.ssk.maintenance_requests;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

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

    @GetMapping(path = "/countOpenRequests")
    public Map<String, Integer> countOpenRequests(){
        return Collections.singletonMap("count", maintenanceRequestService.countOpenRequests());
    }

    @GetMapping(path = "/countCompletedRequests")
    public Map<String, Integer> countCompletedRequests(){
        return Collections.singletonMap("count",  maintenanceRequestService.countCompletedRequests());
    }

    @GetMapping(path = "/countPendingRequests")
    public Map<String, Integer> countPendingRequests(){
        return Collections.singletonMap("count", maintenanceRequestService.countPendingRequests());
    }

    @GetMapping(path = "/getAllPendingRequests")
    public  List<MaintenanceRequestDto> getAllPendingRequests(){
        return maintenanceRequestService.getAllPendingRequests();
    }


    // SYSTEM DETERMINES THE PERSON LOGGED IN (TEMP SOLN, PASS TENANT ID IN URL) FIND APT AND ASSIGN TO REQUEST
    @PutMapping(path = "/update/{id}")
    public void update(@PathVariable Long id, @RequestBody TenantUpdateMaintenanceRequestDto tenantUpdateMaintenanceRequestDto){
        maintenanceRequestService.updateMaintenanceRequest(id, tenantUpdateMaintenanceRequestDto);
    }

    @PostMapping(path = "/create/{tenantId}")
    public ResponseEntity<MaintenanceRequest> createNewMaintenanceRequest(@PathVariable("tenantId") Long tenantId, @RequestBody TenantUpdateMaintenanceRequestDto tenantUpdateMaintenanceRequestDto){
        MaintenanceRequest maintenanceRequest = maintenanceRequestService.createNewMaintenanceRequest(tenantId, tenantUpdateMaintenanceRequestDto);
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
    D - DONE
     */



}
