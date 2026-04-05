package com.dauntlesstechnologies.ssk.tenants;

public class TenantMapper {

    public static TenantDto tenantToDto(Tenant tenant) {
        if(tenant == null) return null;

        return new TenantDto(
                tenant.getId(),
                tenant.getName(),
                tenant.getEmail(),
                tenant.getPhoneNumber(),
                tenant.getAddress(),
                tenant.getFatherName(),
                tenant.getUniqueIdentifier(),
                tenant.isBackgroundChecked(),
                true //Since we are mapping from an object, this has to be true

        );
    }


}
