package com.dauntlesstechnologies.ssk.lease;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface LeaseRepository extends JpaRepository<Lease, Long> {
    Optional<Lease> findByApartmentAndIsActiveTrue(Long apartmentId);
}
