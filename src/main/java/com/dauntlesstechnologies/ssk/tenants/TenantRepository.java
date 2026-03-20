package com.dauntlesstechnologies.ssk.tenants;
import com.dauntlesstechnologies.ssk.apartments.Apartment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TenantRepository extends JpaRepository<Tenant, Long>{
    public List<Tenant> findByNameContainingIgnoreCase(String name);

    public boolean existsByApartmentId(Long id);

    // This finds all tenants linked to an apartment with a specific flatNumber
    public List<Tenant> findByApartmentFlatNumber(String flatNumber);

    //making sure we don't clash with a previous main owner
    @Query("SELECT t FROM Tenant t WHERE t.apartment.flatNumber = :flatNumber AND t.mainOwner = :mainOwner AND t.exists = :existsStatus")
    Optional<Tenant> findActiveMainOwnerByFlatNumber(
            @Param("flatNumber") String flatNumber,
            @Param("mainOwner") boolean mainOwner,
            @Param("existsStatus") boolean existsStatus
    );

    List<Tenant> findByApartment(Apartment apartment);
}



