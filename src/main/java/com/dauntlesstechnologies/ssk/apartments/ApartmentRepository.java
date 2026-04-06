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

    @Query("SELECT DISTINCT a FROM Apartment a JOIN a.leases l WHERE l.isActive = true")
    List<Apartment> findAllOccupiedApartments();

}

