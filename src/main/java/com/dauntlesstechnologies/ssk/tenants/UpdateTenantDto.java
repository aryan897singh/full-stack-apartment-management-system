package com.dauntlesstechnologies.ssk.tenants;


public record UpdateTenantDto(
        String name,
        String email,
        String phoneNumber,
        String address,
        String fatherName,
        String uniqueIdentifier,
        boolean isBackgroundChecked
) {
}
