package com.dauntlesstechnologies.ssk.apartments;

import com.dauntlesstechnologies.ssk.lease.Lease;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ApartmentRepository extends JpaRepository<Apartment, Long> {

    public Optional<Apartment> findByFlatNumber(String flatNumber);

    //Can you confirm if this is a valid method? Apartment holds the Set of Leases
    public Optional<Apartment> findApartmentByLeaseId(Long leaseId);

}

