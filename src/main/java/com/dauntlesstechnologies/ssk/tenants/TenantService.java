package com.dauntlesstechnologies.ssk.tenants;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;


@Service
public class TenantService {

    private final TenantRepository tenantRepository;

    TenantService(TenantRepository tenantRepository) {
        this.tenantRepository = tenantRepository;
    }

    public List<TenantDto> createAndSearchTenantRecord(String name) {
        List<Tenant> foundTenants = tenantRepository.findByNameContainingIgnoreCase(name);
        if (!(foundTenants.isEmpty())) {
            return foundTenants
                                .stream()
                                .map(this::convertToDto)
                                .toList();
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
                tenant.getUniqueIdentifier(),
                tenant.isBackgroundChecked(),
                tenant.getExists()
        );

    }

    public void updateTenant(UpdateTenantDto updateTenantDto, Long id){
        Optional<Tenant> tenantOptional = tenantRepository.findById(id);

        if (tenantOptional.isPresent()){
            Tenant tenant = tenantOptional.get();

            tenant.setName(updateTenantDto.name());
            tenant.setEmail(updateTenantDto.email());
            tenant.setPhoneNumber(updateTenantDto.phoneNumber());
            tenant.setAddress(updateTenantDto.address());
            tenant.setFatherName(updateTenantDto.fatherName());

            tenantRepository.save(tenant);
        }
        else {
            throw new RuntimeException("TENANT NOT FOUND");
        }

    }

    @Transactional
    public TenantDto createTenant(UpdateTenantDto updateTenantDto){
        Tenant tenant = new Tenant();
        tenant.setName(updateTenantDto.name());
        tenant.setEmail(updateTenantDto.email());
        tenant.setPhoneNumber(updateTenantDto.phoneNumber());
        tenant.setAddress(updateTenantDto.address());
        tenant.setFatherName(updateTenantDto.fatherName());
        tenant.setUniqueIdentifier(updateTenantDto.uniqueIdentifier());
        tenant.setBackgroundChecked(updateTenantDto.isBackgroundChecked());
        tenant.setExists(true);

        tenantRepository.save(tenant);
        return convertToDto(tenant);
    }

    public void deleteTenant(Long id){
        Optional<Tenant> tenantOptional = tenantRepository.findById(id);

        if (tenantOptional.isPresent()){
            Tenant tenant = tenantOptional.get();

            //Soft deleting the tenant:
            tenant.setExists(false);
            //interview discussion - problem was that the leave date was not being set, forgot to save it to the repo
            tenantRepository.save(tenant);
        }
        else {
            throw new EntityNotFoundException("TENANT NOT FOUND");
        }
    }


}

