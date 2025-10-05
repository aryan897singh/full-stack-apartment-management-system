package com.dauntlesstechnologies.ssk.maintenance_requests;

import com.dauntlesstechnologies.ssk.apartments.Apartment;
import com.dauntlesstechnologies.ssk.apartments.ApartmentRepository;
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
    private final ApartmentRepository apartmentRepository;
    private final ManagerRepository managerRepository;
    private final TenantRepository tenantRepository;

    //injecting the repo in cause service talks to repo
    public MaintenanceRequestService(MaintenanceRequestRepository maintenanceRequestRepository, ApartmentRepository apartmentRepository , ManagerRepository managerRepository, TenantRepository tenantRepository) {
        this.tenantRepository = tenantRepository;
        this.maintenanceRequestRepository = maintenanceRequestRepository;
        this.apartmentRepository = apartmentRepository;

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
                maintenanceRequest.getApartment().getFlatNumber(),
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

            maintenanceRequestRepository.save(maintenanceRequest);

        }

    }

    public MaintenanceRequest createNewMaintenanceRequest(Long tenantId, TenantUpdateMaintenanceRequestDto tenantUpdateMaintenanceRequestDto){
        MaintenanceRequest maintenanceRequest = new MaintenanceRequest();

//        Optional<Apartment> apartmentOptional = apartmentRepository.findById(tenantUpdateMaintenanceRequestDto.tenantId());

        Optional<Tenant>  optionalTenant = tenantRepository.findById(tenantId);
        if(optionalTenant.isEmpty()){
            throw new RuntimeException("Tenant with id " + tenantId + " not found");
        }else{
            Tenant tenant =  optionalTenant.get();
            Apartment apartment = tenant.getApartment();

            maintenanceRequest.setApartment(apartment);
        }


        Set<Manager> managerSet = managerRepository.findByMaintenanceTypesContaining(tenantUpdateMaintenanceRequestDto.maintenanceType());

        if(!managerSet.isEmpty()){
            Manager manager = managerSet.iterator().next();
            maintenanceRequest.setManager(manager);
        }else{
            throw new RuntimeException("Manager not found with given maintenance request type");
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

    public int countOpenRequests(){
        return maintenanceRequestRepository.countByStatus(Status.IN_PROGRESS);
    }

    public int countCompletedRequests(){
        return maintenanceRequestRepository.countByStatus(Status.COMPLETED);
    }

    public int countPendingRequests(){
        return maintenanceRequestRepository.countByStatus(Status.PENDING);
    }

    public List<MaintenanceRequestDto> getAllPendingRequests(){
        List<MaintenanceRequest> maintenanceRequests = maintenanceRequestRepository.findAllByStatus(Status.PENDING);

        List<MaintenanceRequestDto>  maintenanceRequestDtos = new ArrayList<>();

        for(MaintenanceRequest maintenanceRequest : maintenanceRequests){
            maintenanceRequestDtos.add(entityToDto(maintenanceRequest));
        }

        return  maintenanceRequestDtos;
    }


}
