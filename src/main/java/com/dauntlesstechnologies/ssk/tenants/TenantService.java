package com.dauntlesstechnologies.ssk.tenants;

import com.dauntlesstechnologies.ssk.lease.LeaseRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;


@Service
public class TenantService {

    private final TenantRepository tenantRepository;
    private final LeaseRepository leaseRepository;

    TenantService(TenantRepository tenantRepository, LeaseRepository leaseRepository) {
        this.tenantRepository = tenantRepository;
        this.leaseRepository = leaseRepository;
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
        boolean hasActiveLease = leaseRepository.hasActiveLeaseByTenantId(tenant.getId());

        return new TenantDto(
                tenant.getId(),
                tenant.getName(),
                tenant.getEmail(),
                tenant.getPhoneNumber(),
                tenant.getAddress(),
                tenant.getFatherName(),
                tenant.getUniqueIdentifier(),
                tenant.isBackgroundChecked(),
                hasActiveLease
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
            tenant.setUniqueIdentifier(updateTenantDto.uniqueIdentifier());
            tenant.setBackgroundChecked(updateTenantDto.isBackgroundChecked());

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

        tenantRepository.save(tenant);
        return convertToDto(tenant);
    }


}

