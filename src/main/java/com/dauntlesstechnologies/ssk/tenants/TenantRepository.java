package com.dauntlesstechnologies.ssk.tenants;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TenantRepository extends JpaRepository<Tenant, Long>{
    public List<Tenant> findByNameContainingIgnoreCase(String name);

    public boolean existsByApartmentId(Long id);

    }



