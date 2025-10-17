package com.dauntlesstechnologies.ssk.apartments;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ApartmentRepository extends JpaRepository<Apartment, Long> {

    public Optional<Apartment> findByFlatNumber(String flatNumber);

    //This method is to fetch the number of vacant flats
    public int countByOccupiedIsFalse();

    //This method is to fetch the number of occupied flats
    public int countByOccupiedIsTrue();

    public List<Apartment> findByOccupiedIsFalse();

    //Adding a method to update monthly payment records associated with apt
    @Modifying
    @Query("UPDATE Apartment a SET a.paidRent = a.paidRent + :rent, a.paidMaintenance = a.paidMaintenance + :maintenance WHERE a.id = :id")
    public void addPaymentToApartment(
            @Param("id") Long apartmentId,
            @Param("rent") BigDecimal rentToAdd,
            @Param("maintenance") BigDecimal maintenanceToAdd
    );

    @Query("SELECT a FROM Apartment a WHERE (a.paidRent + a.paidMaintenance) < (a.rentAmount + a.maintenanceAmount)")
    List<Apartment> findApartmentsWithOutstandingRent();

}

