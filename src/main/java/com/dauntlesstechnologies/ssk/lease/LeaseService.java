package com.dauntlesstechnologies.ssk.lease;

import com.dauntlesstechnologies.ssk.apartments.Apartment;
import com.dauntlesstechnologies.ssk.apartments.ApartmentRepository;
import com.dauntlesstechnologies.ssk.tenants.*;
import jakarta.transaction.Transactional;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class LeaseService {

    private final LeaseRepository leaseRepository;
    private final ApartmentRepository apartmentRepository;
    private final TenantRepository tenantRepository;

    public LeaseService(LeaseRepository leaseRepository, ApartmentRepository apartmentRepository, TenantRepository tenantRepository) {
        this.leaseRepository = leaseRepository;
        this.apartmentRepository = apartmentRepository;
        this.tenantRepository = tenantRepository;
    }

    @Transactional
    public LeaseDto createLease(CreateLeaseDto createLeaseDto){
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
        return leaseToDto(lease);
    }

    public LeaseDto getCurrentLeaseFromApartment(String flatNumber){

        Optional<Apartment> apartmentOptional = apartmentRepository.findByFlatNumber(flatNumber);

        if(apartmentOptional.isPresent()){
            Apartment apartment = apartmentOptional.get();
            Optional<Lease> leaseOptional = leaseRepository.findByApartmentIdAndIsActiveTrue(apartment.getId());
            if(leaseOptional.isPresent()){
                return leaseToDto(leaseOptional.get());
            } else throw new RuntimeException("Active Lease for this Apartment not found");


        }else throw new RuntimeException("Apartment not found");



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

    public LeaseDto leaseToDto(Lease lease){

        Set<Tenant> tenants = lease.getTenants();
        Set<TenantDto> tenantDtos = new HashSet<>();

        //Use tenant mapper instead of tenant service here
        for(Tenant tenant : tenants){
           tenantDtos.add(TenantMapper.tenantToDto(tenant));
        }

        return new LeaseDto(
                lease.getStart(),
                lease.getEnd(),
                lease.getApartment().getFlatNumber(), //We make 2 queries here
                tenantDtos,
                lease.getRentAmount(),
                lease.getMaintenanceAmount(),
                lease.getDepositAmount(),
                lease.getDepositCollected(),
                lease.getDepositReturned()
        );


    }

    @Scheduled(cron = "0 0 0 * * ?")
    @Transactional
    public void autoExpireCompletedLeases(){
        System.out.println("RUNNING NIGHTLY LEASE EXPIRATION BATCH JOB: ");

        List<Lease> expiredLeases = leaseRepository.findAllByIsActiveTrueAndEndBefore(new Date());

        if(!expiredLeases.isEmpty()){
            for(Lease lease : expiredLeases){
                lease.setActive(false);

            }
            //One query to update all rather than sending N queries to update each lease
            leaseRepository.saveAll(expiredLeases);
            System.out.println("SUCCESSFULLY DEACTIVATED " + expiredLeases.size() + " EXPIRED LEASES.");
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
