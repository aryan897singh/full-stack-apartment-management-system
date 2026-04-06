package com.dauntlesstechnologies.ssk.tenants;


public record TenantDto(
        Long id,
        String name,
        String email,
        String phoneNumber,
        String address,
        String fatherName,
        String uniqueIdentifier,
        boolean isBackgroundChecked,
        boolean hasActiveLease
) {
}
