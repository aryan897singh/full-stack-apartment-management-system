package com.dauntlesstechnologies.ssk.tenants;

import com.dauntlesstechnologies.ssk.apartments.Apartment;

public record UpdateTenantDto(
        String name,
        String email,
        String phoneNumber,
        String address,
        String fatherName,
        String aadharCardNumber,
        boolean criminalHistory,
        boolean agreementSigned,
        String flatNumber
) {
}
