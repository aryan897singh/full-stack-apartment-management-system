package com.dauntlesstechnologies.ssk.maintenance_requests;

import com.dauntlesstechnologies.ssk.manager.Manager;
import com.dauntlesstechnologies.ssk.manager.ManagerRepository;
import com.dauntlesstechnologies.ssk.tenants.Tenant;
import com.dauntlesstechnologies.ssk.tenants.TenantRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.Date;

@Service
public class MaintenanceRequestService {

    private final MaintenanceRequestRepository maintenanceRequestRepository;
    private final TenantRepository tenantRepository;
    private final ManagerRepository managerRepository;

    //injecting the repo in cause service talks to repo
    public MaintenanceRequestService(MaintenanceRequestRepository maintenanceRequestRepository, TenantRepository tenantRepository, ManagerRepository managerRepository) {
        this.maintenanceRequestRepository = maintenanceRequestRepository;
        this.tenantRepository = tenantRepository; //INTERVIEW PROBLEM SOLUTION: Reason for injecting tenant repo is that our entity stores a full tenant object
                                                //but our updateDto will take in the id only, and so we need to first map the id to
                                                //the tenant itself and then set it according to what we find, if we find it

        this.managerRepository = managerRepository; //INTERVIEW PROBLEM SOLUTION - We need to obtain the manager by the MaintenanceType
                                                    //because obv the user doesnt know the manager, but the manager should be set so that
                                                    //they are immediately sent a notification of the maintenance request :) (next version feature)
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
                maintenanceRequest.getManager().getId(),
                maintenanceRequest.getMaintenanceType(),
                maintenanceRequest.getTitle(),
                maintenanceRequest.getDescription(),
                maintenanceRequest.getStatus(),
                maintenanceRequest.getDateSubmitted()

        );

    }

    public List<MaintenanceRequestDto> getAllMaintenanceRequests(){
       List<MaintenanceRequest> maintenanceRequests = maintenanceRequestRepository.findAll();
       List<MaintenanceRequestDto> maintenanceRequestDtos = new ArrayList<>();

       for(MaintenanceRequest maintenanceRequest : maintenanceRequests){
           maintenanceRequestDtos.add(entityToDto(maintenanceRequest));

       }

       return maintenanceRequestDtos;

    }

    public void updateMaintenanceRequest(Long id, TenantUpdateMaintenanceRequestDto tenantUpdateMaintenanceRequestDto){

        Optional<MaintenanceRequest> optionalMaintenanceRequest = maintenanceRequestRepository.findById(id);

        if(optionalMaintenanceRequest.isPresent()){

            MaintenanceRequest maintenanceRequest = optionalMaintenanceRequest.get();

            maintenanceRequest.setMaintenanceType(tenantUpdateMaintenanceRequestDto.maintenanceType());

            Set<Manager> manager = managerRepository.findByMaintenanceTypesContaining(tenantUpdateMaintenanceRequestDto.maintenanceType());


            if(manager.isEmpty()){
                throw new RuntimeException("Manager with mentioned Enum type not found");
            }else{
                Manager assignedManager = manager.iterator().next();
                maintenanceRequest.setManager(assignedManager);
            }

            maintenanceRequest.setTitle(tenantUpdateMaintenanceRequestDto.title());
            maintenanceRequest.setDescription(tenantUpdateMaintenanceRequestDto.description());
            maintenanceRequest.setDateSubmitted(new Date());

            maintenanceRequestRepository.save(maintenanceRequest);

        }

    }

    public MaintenanceRequest createNewMaintenanceRequest(TenantUpdateMaintenanceRequestDto tenantUpdateMaintenanceRequestDto){
        MaintenanceRequest maintenanceRequest = new MaintenanceRequest();

        Optional<Tenant> tenantOptional = tenantRepository.findById(tenantUpdateMaintenanceRequestDto.tenantId());

        if(tenantOptional.isPresent()){
            Tenant tenant = tenantOptional.get();
            maintenanceRequest.setTenant(tenant);
        }
        else{
            throw new RuntimeException("Tenant not found");
        }

        maintenanceRequest.setMaintenanceType(tenantUpdateMaintenanceRequestDto.maintenanceType());
        maintenanceRequest.setTitle(tenantUpdateMaintenanceRequestDto.title());
        maintenanceRequest.setDescription(tenantUpdateMaintenanceRequestDto.description());
        maintenanceRequest.setDateSubmitted(new Date());

        return maintenanceRequestRepository.save(maintenanceRequest);
    }

    public void deleteMaintenanceRequest(Long id){

        Optional<MaintenanceRequest> maintenanceRequestOptional = maintenanceRequestRepository.findById(id);

        if(maintenanceRequestOptional.isPresent()){
            maintenanceRequestRepository.deleteById(id);
        }
        else{
            throw new RuntimeException("MaintenanceRequest with id " + id + " not found");
        }

    }


}
