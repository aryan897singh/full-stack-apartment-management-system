package com.dauntlesstechnologies.ssk.lease;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;


@Repository
public interface LeaseRepository extends JpaRepository<Lease, Long> {
    Optional<Lease> findByApartmentIdAndIsActiveTrue(Long apartmentId);

    // Writing custom query to delegate the search to SQL - Pro: Extremely fast Con: Becomes Non DB Agnostic due to custom MySQL query
    @Query("SELECT MAX(l.end) FROM Lease l WHERE l.apartment.id = :apartmentId AND l.isActive = false")
    Optional<Date> findLastOccupiedDateByApartmentId(@Param("apartmentId") Long apartmentId);
}
