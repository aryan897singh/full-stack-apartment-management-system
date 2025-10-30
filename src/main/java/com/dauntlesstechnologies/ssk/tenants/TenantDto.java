package com.dauntlesstechnologies.ssk.tenants;

import com.dauntlesstechnologies.ssk.apartments.Apartment;

import java.util.Date;

public record TenantDto(
        Long id,
        String name,
        boolean mainOwner,
        String email,
        String phoneNumber,
        String address,
        String fatherName,
        Long apartmentId,
        String flatNumber,
        String aadharCardNumber,
        boolean criminalHistory,
        boolean agreementSigned,
        Date joinDate,
        Date leaveDate,
        boolean exists
) {
}
