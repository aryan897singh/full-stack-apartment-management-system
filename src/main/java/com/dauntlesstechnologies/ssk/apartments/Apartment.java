package com.dauntlesstechnologies.ssk.apartments;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "apartment_tbl")
public class Apartment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "flat_number", unique = true, nullable = false)
    private String flatNumber;

    @Column
    private BigDecimal rentAmount;

    @Column(name = "rent_outstanding")
    private Boolean rentOutstanding;

    @Column
    private Boolean occupied;

    @Column(name = "last_occupied")
    private Date lastOccupied;

    public Boolean getOccupied() {
        return occupied;
    }

    public void setOccupied(Boolean occupied) {
        this.occupied = occupied;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFlatNumber() {
        return flatNumber;
    }

    public void setFlatNumber(String flatNumber) {
        this.flatNumber = flatNumber;
    }

    public Boolean getRentOutstanding() {
        return rentOutstanding;
    }

    public void setRentOutstanding(Boolean rentOutstanding) {
        this.rentOutstanding = rentOutstanding;
    }

    public BigDecimal getRentAmount() {
        return rentAmount;
    }

    public void setRentAmount(BigDecimal rentAmount) {
        this.rentAmount = rentAmount;
    }

    public Date getLastOccupied() {
        return lastOccupied;
    }

    public void setLastOccupied(Date lastOccupied) {
        this.lastOccupied = lastOccupied;
    }
}
