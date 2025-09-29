package com.dauntlesstechnologies.ssk.furniture;

public record FurnitureDto(
        Long id,
        String flatNumber,
        FurnitureType furnitureType,
        int quantity
) {
}
