package com.dauntlesstechnologies.ssk.tenants;

import com.dauntlesstechnologies.ssk.apartments.Apartment;

public record TenantDto(
        Long id,
        String name,
        String email,
        String phoneNumber,
        String address,
        String fatherName,
        Long apartmentId,
        String flatNumber,
        String aadharCardNumber,
        boolean criminalHistory,
        boolean agreementSigned
) {
}
