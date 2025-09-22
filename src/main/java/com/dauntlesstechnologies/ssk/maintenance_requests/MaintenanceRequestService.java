package com.dauntlesstechnologies.ssk.maintenance_requests;

import com.dauntlesstechnologies.ssk.tenants.TenantDto;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MaintenanceRequestService {

    private final MaintenanceRequestRepository maintenanceRequestRepository;

    //injecting the repo in cause service talks to repo
    public MaintenanceRequestService(MaintenanceRequestRepository maintenanceRequestRepository){
        this.maintenanceRequestRepository = maintenanceRequestRepository;
    }

    public MaintenanceRequestDto findMaintenanceRequestById(Long id){
        Optional<MaintenanceRequest> maintenanceRequestOptional = maintenanceRequestRepository.findById(id);

        if(maintenanceRequestOptional.isPresent()){
            return entityToDto(maintenanceRequestOptional.get());
        }
        else {
            throw new EntityNotFoundException("MaintenanceRequest with id " + id + " not found");
        }
    }

    public MaintenanceRequestDto entityToDto(MaintenanceRequest maintenanceRequest){
        return new MaintenanceRequestDto(
                maintenanceRequest.getId(),
                maintenanceRequest.getTenant().getId(),
            //maintenanceRequest.getManager(),
                maintenanceRequest.getMaintenanceType(),
                maintenanceRequest.getTitle(),
                maintenanceRequest.getDescription(),
                maintenanceRequest.getStatus(),
                maintenanceRequest.getDateSubmitted()

        );

    }


}
