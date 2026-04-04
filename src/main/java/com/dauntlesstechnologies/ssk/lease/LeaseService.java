package com.dauntlesstechnologies.ssk.lease;

import com.dauntlesstechnologies.ssk.apartments.Apartment;
import com.dauntlesstechnologies.ssk.apartments.ApartmentRepository;
import com.dauntlesstechnologies.ssk.tenants.Tenant;
import com.dauntlesstechnologies.ssk.tenants.TenantRepository;
import com.dauntlesstechnologies.ssk.tenants.UpdateTenantDto;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class LeaseService {

    private final LeaseRepository leaseRepository;
    private final ApartmentRepository apartmentRepository;
    private final TenantRepository tenantRepository;

    public LeaseService(LeaseRepository leaseRepository, ApartmentRepository apartmentRepository, TenantRepository tenantRepository ) {
        this.leaseRepository = leaseRepository;
        this.apartmentRepository = apartmentRepository;
        this.tenantRepository = tenantRepository;

    }

    @Transactional
    public Lease createLease(CreateLeaseDto createLeaseDto){
        Lease lease = new Lease();
        lease.setStart(createLeaseDto.start());
        lease.setEnd(createLeaseDto.end());
        Optional<Apartment> apartmentOptional = apartmentRepository.findByFlatNumber(createLeaseDto.flatNumber());


        if(apartmentOptional.isPresent()){
            //Now that we have found an apartment, we need to look at the current active lease and deactivate it
            Apartment apartment =  apartmentOptional.get();

            Optional<Lease> oldLease = leaseRepository.findByApartmentIdAndIsActiveTrue(apartment.getId());
            //This ensures we load only 1 Lease entity in RAM rather than all the leases in history and looping through them
            //to find the current active lease, we delegate the querying to SQL
            if(oldLease.isPresent()){
                oldLease.get().setActive(false);
            } //Possible that this is the first time the owner is making a lease for an apt

            lease.setActive(true);
            lease.setApartment(apartment);

        }else{
            throw new RuntimeException("Apartment not found");
        }

        Set<Tenant> tenants = new HashSet<>();
        for(Long id: createLeaseDto.tenantIds()){
            tenants.add(getTenantFromId(id));
        }
        lease.setTenants(tenants);

        lease.setRentAmount(createLeaseDto.rentAmount());
        lease.setMaintenanceAmount(createLeaseDto.maintenanceAmount());
        lease.setDepositAmount(createLeaseDto.depositAmount());
        lease.setDepositCollected(createLeaseDto.isDepositCollected());

        leaseRepository.save(lease);
        return lease;
    }

    @Transactional
    public void updateLease(UpdateLeaseDto updateLeaseDto, Long leaseId){
        Optional<Lease> leaseOptional = leaseRepository.findById(leaseId);
        Lease lease = new Lease();
        if(leaseOptional.isPresent()){
            lease = leaseOptional.get();
        }else{
            throw new RuntimeException("Lease not found");
        }

        lease.setStart(updateLeaseDto.start());
        lease.setEnd(updateLeaseDto.end());
        Optional<Apartment> apartmentOptional = apartmentRepository.findByFlatNumber(updateLeaseDto.flatNumber());

        if(apartmentOptional.isPresent()){
            lease.setApartment(apartmentOptional.get());
        }else{
            throw new RuntimeException("Apartment not found");
        }

        Set<Tenant> tenants = new HashSet<>();
        for(Long id: updateLeaseDto.tenantIds()){
            tenants.add(getTenantFromId(id));
        }

        lease.setRentAmount(updateLeaseDto.rentAmount());
        lease.setMaintenanceAmount(updateLeaseDto.maintenanceAmount());
        lease.setDepositAmount(updateLeaseDto.depositAmount());
        lease.setDepositCollected(updateLeaseDto.isDepositCollected());

        leaseRepository.save(lease);

    }


    public Tenant getTenantFromId(Long tenantId){
        Optional<Tenant> tenantOptional = tenantRepository.findById(tenantId);
        if(tenantOptional.isPresent()){
            return tenantOptional.get();
        }
        else{
            throw new RuntimeException("Tenant not found");
        }

    }





}



/*
This is the Service layer, aka business logic, we need to do:

//So we want to take some elements in the form of a DTO

C - Creating a Lease
R
U
D


 */
