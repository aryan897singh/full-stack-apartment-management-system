package com.dauntlesstechnologies.ssk.apartments;

import java.math.BigDecimal;
import java.util.Date;

public record ApartmentDto(
        Long id,
        String flatNumber,
        BigDecimal expectedRent,
        BigDecimal rentAmount,
        BigDecimal maintenanceAmount,
        BigDecimal paidMaintenance,
        BigDecimal paidRent,
        Boolean occupied,
        Date lastOccupied,
        Boolean depositCollected
        ) {

    // It's a static "factory" that knows how to build an
    // ApartmentDto from an Apartment entity.
    public static ApartmentDto fromEntity(Apartment apartment) {
        if (apartment == null) {
            return null;
        }
        return new ApartmentDto(
                apartment.getId(),
                apartment.getFlatNumber(),
                apartment.getExpectedRent(),
                apartment.getRentAmount(),
                apartment.getMaintenanceAmount(),
                apartment.getPaidMaintenance(),
                apartment.getPaidRent(),
                apartment.getOccupied(),
                apartment.getLastOccupied(),
                apartment.getDepositCollected()
        );
    }
}
