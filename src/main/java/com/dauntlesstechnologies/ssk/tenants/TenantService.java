package com.dauntlesstechnologies.ssk.tenants;

import com.dauntlesstechnologies.ssk.apartments.Apartment;
import com.dauntlesstechnologies.ssk.apartments.ApartmentRepository;
import com.dauntlesstechnologies.ssk.apartments.ApartmentService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TenantService {

    private final TenantRepository tenantRepository;
    private final ApartmentRepository apartmentRepository;


    //injected the repo to service layer
    TenantService(TenantRepository tenantRepository, ApartmentRepository apartmentRepository){
        this.tenantRepository = tenantRepository;
        this.apartmentRepository = apartmentRepository;
    }

    public List<TenantDto> getAllTenants(){
        List<TenantDto> tenantDtos = new ArrayList<>();
        List<Tenant> tenants = tenantRepository.findEverything();

        System.out.println("Number of tenants found: " + tenants.size());

        for(int i = 0; i < tenants.size(); i++ ){
            tenantDtos.add(convertToDto(tenants.get(i)));
        }
        return tenantDtos;
    }

    public List<TenantDto> createAndSearchTenantRecord(String name) {
        List<Tenant> foundTenants = tenantRepository.findByNameContainingIgnoreCase(name);
        if (!(foundTenants.isEmpty())) {
            return foundTenants.stream().map(this::convertToDto).toList();
        }
        else{
            throw new RuntimeException("Tenant NOT FOUND");
        }
    }

    public TenantDto findById(Long id){
        Tenant tenant;
        Optional<Tenant> tenantOptional = tenantRepository.findById(id);
        if (tenantOptional.isPresent()){
             tenant = tenantOptional.get();
        }
        else {
            throw new RuntimeException("NO SUCH PERSON FOUND");
        }

        return convertToDto(tenant);

    }

    private TenantDto convertToDto(Tenant tenant) {
        return new TenantDto(
                tenant.getId(),
                tenant.getName(),
                tenant.getEmail(),
                tenant.getPhoneNumber(),
                tenant.getAddress(),
                tenant.getFatherName(),
                tenant.getApartment().getId(),
                tenant.getFlatNumber(),
                tenant.getAadharCardNumber(),
                tenant.isCriminalHistory(),
                tenant.isAgreementSigned(),
                tenant.getJoinDate(),
                tenant.getLeaveDate()
        );

    }

    //Remember Post It analogy
    //Struggled here, rem for interviews, user cant enter id, URL gives that, then what?
    //I created a new DTO type without id, took the info, found the specific entity with the
    //id provided by the URL, and modified its info with the updated info
    //===================================================================================
    //We find the entity with the correct id, and replace the info with the info provided by user
    public void updateTenant(UpdateTenantDto updateTenantDto, Long id){
        Optional<Tenant> tenantOptional = tenantRepository.findById(id);

        if (tenantOptional.isPresent()){
            Tenant tenant = tenantOptional.get();

            tenant.setName(updateTenantDto.name());
            tenant.setEmail(updateTenantDto.email());
            tenant.setPhoneNumber(updateTenantDto.phoneNumber());
            tenant.setAddress(updateTenantDto.address());
            tenant.setFatherName(updateTenantDto.fatherName());
            tenant.setAadharCardNumber(updateTenantDto.aadharCardNumber());
            tenant.setJoinDate(updateTenantDto.joinDate());

            //Note: this basically does an INSERT statement if id doesnt exist and UPDATE if it does
            tenantRepository.save(tenant);

        }
        else {
            throw new RuntimeException("TENANT NOT FOUND");
        }

    }

    public Tenant createTenant(UpdateTenantDto tenantDto){
        Tenant tenant = new Tenant();
        tenant.setName(tenantDto.name());
        tenant.setEmail(tenantDto.email());
        tenant.setPhoneNumber(tenantDto.phoneNumber());
        tenant.setAddress(tenantDto.address());
        tenant.setFatherName(tenantDto.fatherName());
        tenant.setAadharCardNumber(tenantDto.aadharCardNumber());
        tenant.setFlatNumber(tenantDto.flatNumber());
        tenant.setCriminalHistory(tenantDto.criminalHistory());
        tenant.setAgreementSigned(tenantDto.agreementSigned());
        tenant.setJoinDate(new Date());

        Optional<Apartment> apartmentOptional = apartmentRepository.findByFlatNumber(tenantDto.flatNumber());

        if(apartmentOptional.isPresent()){
            tenant.setApartment(apartmentOptional.get());
        }
        else {
            throw new RuntimeException("NO SUCH APARTMENT FOUND WITH GIVEN APT NUMBER");
        }
        tenantRepository.save(tenant);
        return tenant;
    }

    public void deleteTenant(Long id){

        Optional<Tenant> tenantOptional = tenantRepository.findById(id);

        if (tenantOptional.isPresent()){
            Tenant tenant = tenantOptional.get();
            tenant.setLeaveDate(new Date());
            //interview discussion - problem was that the leave date was not being set, forgot to save it to the repo
            tenantRepository.save(tenant);
            //NOW HAVE TO ADD DELETED TENANT'S DETAILS INTO TENANT HISTORY ENTITY, CREATE LATER - CLIENT REQUIREMENT
            tenantRepository.deleteById(id);
        }
        else {
            throw new RuntimeException("NO SUCH TENANT FOUND TO DELETE");
        }



    }
}
