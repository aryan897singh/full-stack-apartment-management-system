package com.dauntlesstechnologies.ssk.apartments;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApartmentRepository extends JpaRepository<Apartment, Long> {

    public Optional<Apartment> findByFlatNumber(String flatNumber);

    //This method is to fetch the number of vacant flats
    public int countByOccupiedIsFalse();

    //This method is to fetch the number of occupied flats
    public int countByOccupiedIsTrue();
}

