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


    @OneToMany(mappedBy = "apartment", fetch = FetchType.LAZY)
    private Set<Lease> leases;

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

    public Set<Lease> getLeases() {
        return leases;
    }

    public void setLeases(Set<Lease> leases) {
        this.leases = leases;
    }
}
