package com.dauntlesstechnologies.ssk.tenants;

public class TenantMapper {

    public static TenantDto tenantToDto(Tenant tenant) {
        if(tenant == null) return null;

        return new TenantDto(
                tenant.getId(),
                tenant.getName(),
                tenant.isMainOwner(),
                tenant.getEmail(),
                tenant.getPhoneNumber(),
                tenant.getAddress(),
                tenant.getFatherName(),
                tenant.getAadharCardNumber(),
                tenant.isCriminalHistory(),
                tenant.isAgreementSigned(),
                tenant.getJoinDate(),
                tenant.getLeaveDate(),
                tenant.getExists()
        );
    }


}
