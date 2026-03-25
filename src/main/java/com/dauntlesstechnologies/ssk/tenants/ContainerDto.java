package com.dauntlesstechnologies.ssk.tenants;

import com.dauntlesstechnologies.ssk.apartments.ApartmentDto;

import java.util.List;

public record ContainerDto(
        List<TenantDto> tenantDtoList,
        ApartmentDto apartmentDto
) {
}
