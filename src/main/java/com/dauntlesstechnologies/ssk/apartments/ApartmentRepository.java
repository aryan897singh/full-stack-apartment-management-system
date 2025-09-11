package com.dauntlesstechnologies.ssk.apartments;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApartmentRepository extends JpaRepository<Apartment, Long> {

    public Optional<Apartment> findByFlatNumber(String flatNumber);

}
