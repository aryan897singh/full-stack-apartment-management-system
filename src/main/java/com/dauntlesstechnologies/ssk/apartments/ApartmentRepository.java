package com.dauntlesstechnologies.ssk.apartments;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ApartmentRepository extends JpaRepository<Apartment, Long> {

    public Optional<Apartment> findByFlatNumber(String flatNumber);

    //This method is to fetch the number of vacant flats
    public int countByOccupiedIsFalse();

    //This method is to fetch the number of occupied flats
    public int countByOccupiedIsTrue();

    public List<Apartment> findByOccupiedIsFalse();

}

