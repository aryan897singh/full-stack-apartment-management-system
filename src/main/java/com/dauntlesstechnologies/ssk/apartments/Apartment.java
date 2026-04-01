package com.dauntlesstechnologies.ssk.apartments;

import com.dauntlesstechnologies.ssk.lease.Lease;
import jakarta.persistence.*;

import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "apartment_tbl")
public class Apartment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "flat_number")
    private String flatNumber;

    @Column
    private Boolean occupied;

    @Column(name = "last_occupied")
    private Date lastOccupied;

    @Column
    private Boolean depositCollected;

    @OneToMany(mappedBy = "apartment", fetch = FetchType.LAZY)
    private Set<Lease> leases;

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

    public String getFlatNumber() {
        return flatNumber;
    }

    public void setFlatNumber(String flatNumber) {
        this.flatNumber = flatNumber;
    }

    public Set<Lease> getLeases() {
        return leases;
    }

    public void setLeases(Set<Lease> leases) {
        this.leases = leases;
    }
}
