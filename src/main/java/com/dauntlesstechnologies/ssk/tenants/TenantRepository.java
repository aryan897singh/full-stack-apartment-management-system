package com.dauntlesstechnologies.ssk.tenants;
import com.dauntlesstechnologies.ssk.apartments.Apartment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TenantRepository extends JpaRepository<Tenant, Long>{
    public List<Tenant> findByNameContainingIgnoreCase(String name);
}



