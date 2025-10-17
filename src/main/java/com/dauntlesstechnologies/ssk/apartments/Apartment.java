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

    @Column(name = "flat_number")
    private String flatNumber;

    //TO BE SET BY OWNER ONLY, AND IS FIXED
    @Column(name = "expected_rent")
    private BigDecimal expectedRent;

    //This is the actual rent amount aka negotiated rent = negotiated deposit that needs to paid
    @Column(name = "rent_amount")
    private BigDecimal rentAmount;

    @Column(name = "maintenance_amount")
    private BigDecimal maintenanceAmount;

    @Column(name = "paid_maintenance")
    private BigDecimal  paidMaintenance;

    @Column(name = "paid_rent")
    private BigDecimal paidRent;


    @Column
    private Boolean occupied;

    @Column(name = "last_occupied")
    private Date lastOccupied;

    @Column
    private Boolean depositCollected;

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

    public Boolean getDepositCollected() {
        return depositCollected;
    }

    public void setDepositCollected(Boolean depositCollected) {
        this.depositCollected = depositCollected;
    }

    public BigDecimal getExpectedRent() {
        return expectedRent;
    }

    public void setExpectedRent(BigDecimal expectedRent) {
        this.expectedRent = expectedRent;
    }

    public BigDecimal getMaintenanceAmount() {
        return maintenanceAmount;
    }

    public void setMaintenanceAmount(BigDecimal maintenanceAmount) {
        this.maintenanceAmount = maintenanceAmount;
    }

    public BigDecimal getPaidRent() {
        return paidRent;
    }

    public void setPaidRent(BigDecimal paidRent) {
        this.paidRent = paidRent;
    }

    public BigDecimal getPaidMaintenance() {
        return paidMaintenance;
    }

    public void setPaidMaintenance(BigDecimal paidMaintenance) {
        this.paidMaintenance = paidMaintenance;
    }

    public String getFlatNumber() {
        return flatNumber;
    }

    public void setFlatNumber(String flatNumber) {
        this.flatNumber = flatNumber;
    }


}
