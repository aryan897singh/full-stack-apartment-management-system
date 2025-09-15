package com.dauntlesstechnologies.ssk.tenants;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TenantRepository extends JpaRepository<Tenant, Long>{
    public List<Tenant> findByNameContainingIgnoreCase(String name);

    public boolean existsByApartmentId(Long id);

    public List<Tenant> findAll();



    }

