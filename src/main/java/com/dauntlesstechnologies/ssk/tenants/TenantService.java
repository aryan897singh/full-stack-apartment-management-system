package com.dauntlesstechnologies.ssk.tenants;

import com.dauntlesstechnologies.ssk.apartments.Apartment;
import com.dauntlesstechnologies.ssk.apartments.ApartmentDto;
import com.dauntlesstechnologies.ssk.apartments.ApartmentRepository;
import com.dauntlesstechnologies.ssk.deposits.Deposit;
import com.dauntlesstechnologies.ssk.deposits.DepositRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
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
            if(Boolean.TRUE.equals(tenants.get(i).isMainOwner())){
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
        List<Tenant> tenants = tenantRepository.findByApartmentFlatNumber(flatNumber);
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
                (tenant.getApartment() != null) ? tenant.getApartment().getId() : null,
                tenant.getAadharCardNumber(),
                tenant.isCriminalHistory(),
                tenant.isAgreementSigned(),
                tenant.getJoinDate(),
                tenant.getLeaveDate(),
                tenant.getExists(),
                (tenant.getApartment() != null ) ? tenant.getApartment().getFlatNumber() : null
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

    @Transactional
    public Tenant createTenant(UpdateTenantDto tenantDto){
        Tenant tenant = new Tenant();
        tenant.setName(tenantDto.name());
        tenant.setEmail(tenantDto.email());
        tenant.setPhoneNumber(tenantDto.phoneNumber());
        tenant.setAddress(tenantDto.address());
        tenant.setFatherName(tenantDto.fatherName());
        tenant.setAadharCardNumber(tenantDto.aadharCardNumber());
        tenant.setCriminalHistory(tenantDto.criminalHistory());
        tenant.setAgreementSigned(tenantDto.agreementSigned());
        tenant.setJoinDate(new Date());
        tenant.setExists(true);

        Optional<Apartment> apartmentOptional = apartmentRepository.findByFlatNumber(tenantDto.flatNumber());

        Long aptId = 0L;

        if(apartmentOptional.isPresent()){
            aptId = apartmentOptional.get().getId();
            tenant.setApartment(apartmentOptional.get());
        }
        else {
            throw new RuntimeException("NO SUCH APARTMENT FOUND WITH GIVEN APT NUMBER");
        }

        //Here, if no one else exists, set it to true regardless of selection, and if main owner
        //exists set it to false regardless of selection

        //step 1. check if the flat is occupied or not

        Optional<Tenant> tenantOptional = tenantRepository.findActiveMainOwnerByFlatNumber(tenantDto.flatNumber(), true, true);

        if(!tenantRepository.existsByApartmentId(aptId)){
            tenant.setMainOwner(true);
        }else if(tenantOptional.isPresent()){
            tenant.setMainOwner(false);
        }else{
            tenant.setMainOwner(tenantDto.mainOwner());
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

            //ok so now we have the particular apt, and we soft delete the tenant


            tenant.setLeaveDate(new Date());

            //Soft deleting the tenant:
            tenant.setExists(false);
            tenant.setApartment(null);
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

    public ContainerDto getApartmentDetails(Long tenantId){

        Optional<Tenant> tenantOptional = tenantRepository.findById(tenantId);
        if (tenantOptional.isPresent()){
            Tenant tenant = tenantOptional.get();
            Apartment apartment = tenant.getApartment();
            List<TenantDto> tenantDtoList = new ArrayList<>();

            List<Tenant> tenantList = tenantRepository.findByApartment(apartment);

            for(Tenant tenant1 : tenantList){
                tenantDtoList.add(convertToDto(tenant1));
            }

            return new ContainerDto(
                    tenantDtoList,
                    ApartmentDto.fromEntity(apartment)
            );



        }else{
            throw new EntityNotFoundException("TENANT NOT FOUND");
        }



    }
}
