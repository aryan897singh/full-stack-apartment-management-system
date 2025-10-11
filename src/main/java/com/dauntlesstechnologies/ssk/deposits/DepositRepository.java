package com.dauntlesstechnologies.ssk.deposits;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface DepositRepository extends JpaRepository<Deposit,Long> {

    public Optional<Deposit> findByApartmentId(Long apartmentId);

    @Query("SELECT COUNT(d) FROM Deposit d WHERE d.paid >= d.negotiated")
    public long countDepositsCollected();

   //NOTE IMPLEMENT JPQL (NATIVEQUERY = FALSE, BECAUSE JPQL QUERIES TABLES AND NOT! INNER NATIVE
    //mySQL TABLES. THERE ARE MULTIPLE BENEFITS, FIRST BEING THAT YOU CAN USE ANY DB WITHOUT ISSUES
    //Type Safety: You work with your Java objects, so you won't have to worry about mismatched data types or column names.
   //Portability: The query is portable because it's based on your entity model,
   // not on a specific database's syntax.
    @Query(value = "SELECT at.occupied FROM apartment_tbl at, deposits_tbl dt WHERE at.id = dt.apartment_id AND dt.id = :depositId", nativeQuery = true)
    public boolean checkIfAptOccupied(Long depositId); //OPTIMIZE THIS
}
