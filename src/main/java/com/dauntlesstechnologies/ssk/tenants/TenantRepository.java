package com.dauntlesstechnologies.ssk.tenants;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TenantRepository extends JpaRepository<Tenant, Long>{
    public List<Tenant> findByNameContainingIgnoreCase(String name);

    public boolean existsByApartmentId(Long id);

    //For some reason the findAll() method is not working correctly for me
    @Query(value = "SELECT * FROM tenants_tbl LIMIT 1000", nativeQuery = true)
    List<Tenant> findEverything();
    }

