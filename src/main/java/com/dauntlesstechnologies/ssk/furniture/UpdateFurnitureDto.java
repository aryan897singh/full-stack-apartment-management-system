package com.dauntlesstechnologies.ssk.furniture;

public record UpdateFurnitureDto(
        String flatNumber,
        FurnitureType furnitureType,
        int quantity
) {
}
