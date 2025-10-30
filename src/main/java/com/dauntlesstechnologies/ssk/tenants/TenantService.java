package com.dauntlesstechnologies.ssk.tenants;

import com.dauntlesstechnologies.ssk.apartments.Apartment;
import com.dauntlesstechnologies.ssk.apartments.ApartmentRepository;
import com.dauntlesstechnologies.ssk.deposits.Deposit;
import com.dauntlesstechnologies.ssk.deposits.DepositRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
public class TenantService {

    private final TenantRepository tenantRepository;
    private final ApartmentRepository apartmentRepository;
    private final DepositRepository depositRepository;


    //injected the repo to service layer
    TenantService(TenantRepository tenantRepository, ApartmentRepository apartmentRepository,  DepositRepository depositRepository) {
        this.tenantRepository = tenantRepository;
        this.apartmentRepository = apartmentRepository;
        this.depositRepository = depositRepository;
    }

    public List<TenantDto> getAllUniqueTenants(){
        List<TenantDto> tenantDtos = new ArrayList<>();
        List<Tenant> tenants = tenantRepository.findAll();

        System.out.println("Number of tenants found: " + tenants.size());

        for(int i = 0; i < tenants.size(); i++ ){
            //ensures that we are getting all unique tenants for overall review of households occupied
            if(tenants.get(i).isMainOwner()){
                tenantDtos.add(convertToDto(tenants.get(i)));
            }

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

    //for view all details under tenants page
    public List<TenantDto> findByFlatNumber(String flatNumber){
        List<Tenant> tenants = tenantRepository.findByFlatNumber(flatNumber);
        List<TenantDto> tenantDtos = new ArrayList<>();
        for(Tenant tenant : tenants){
            tenantDtos.add(convertToDto(tenant));
        }
        return tenantDtos;
    }

    private TenantDto convertToDto(Tenant tenant) {
        return new TenantDto(
                tenant.getId(),
                tenant.getName(),
                tenant.isMainOwner(),
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
                tenant.getLeaveDate(),
                tenant.getExists()
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
            tenant.setMainOwner(updateTenantDto.mainOwner());
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
        tenant.setMainOwner(tenantDto.mainOwner());
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

            Apartment apartment = tenant.getApartment();
            Long aptId =  apartment.getId();


            tenant.setLeaveDate(new Date());

            //Soft deleting the tenant:
            tenant.setExists(false);
            tenant.setApartment(null);
            tenant.setFlatNumber(null);
            //interview discussion - problem was that the leave date was not being set, forgot to save it to the repo
            tenantRepository.save(tenant);

            //Note that we are not actually deleting, its a soft delete to maintain data history (client requirement)

            //Modifying the deposit layer
            //Noticed one problem, we could have the case where some people leave, others stay
            //the deposit will not be set to null, so we need to check if the apt is empty or not

            //NOTE: The occupied field gets set ONLY WHEN WE RUN THE countOccupiedOrVacant
            //method in the apartment repository, and therefore might not be valid right now
            //we NOW need to check if any tenants associated with the aptId we have

            if(!tenantRepository.existsByApartmentId(aptId)){
                Optional<Deposit> depositOptional = depositRepository.findByApartmentId(aptId);
                if(depositOptional.isPresent()){
                    Deposit deposit = depositOptional.get();
                    deposit.setNegotiated(null);
                    deposit.setPaid(null);
                    depositRepository.save(deposit);
                }else{
                    throw new RuntimeException("NO SUCH DEPOSIT FOUND ASSOCIATED WITH APARTMENT ID + " + aptId);
                }

            }


        }
        else {
            throw new EntityNotFoundException("TENANT NOT FOUND");
        }



    }
}
