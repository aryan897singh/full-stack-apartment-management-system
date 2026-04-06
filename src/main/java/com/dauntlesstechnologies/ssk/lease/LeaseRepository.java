package com.dauntlesstechnologies.ssk.lease;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;


@Repository
public interface LeaseRepository extends JpaRepository<Lease, Long> {
    Optional<Lease> findByApartmentIdAndIsActiveTrue(Long apartmentId);

    // Writing custom query to delegate the search to SQL
    @Query("SELECT MAX(l.end) FROM Lease l WHERE l.apartment.id = :apartmentId AND l.isActive = false")
    Optional<Date> findLastOccupiedDateByApartmentId(@Param("apartmentId") Long apartmentId);

    //This JPQL Query will give us all the leases that end before today's date - hence expired
    List<Lease> findAllByIsActiveTrueAndEndBefore(Date currentDate);

    //This custom JPQL query will generate a MySQL inner join to count the number of active leases that a tenant has
    @Query("SELECT COUNT(l) > 0 FROM Lease l JOIN l.tenants t WHERE t.id = :tenantId AND l.isActive = true")
    boolean hasActiveLeaseByTenantId(@Param("tenantId") Long tenantId);

    @Query("SELECT l from Lease l JOIN l.tenants t WHERE t.id = :tenantid AND l.isActive = true")
    Optional<Lease> findActiveLeaseByTenantId(@Param("tenantId") Long tenantId);

    @Query("SELECT COUNT(DISTINCT l.apartment.id) FROM Lease l WHERE l.isActive = true")
    long countOccupiedApartments();

}
