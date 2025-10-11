package com.dauntlesstechnologies.ssk.deposits;

import com.dauntlesstechnologies.ssk.apartments.Apartment;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "deposits_tbl")
public class Deposit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "apartment_id", referencedColumnName = "id", nullable = false)
    private Apartment apartment;

    @Column
    private BigDecimal expected;

    @Column
    private BigDecimal negotiated;

    @Column
    private BigDecimal paid;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Apartment getApartment() {
        return apartment;
    }

    public void setApartment(Apartment apartment) {
        this.apartment = apartment;
    }

    public BigDecimal getExpected() {
        return expected;
    }

    public void setExpected(BigDecimal expected) {
        this.expected = expected;
    }

    public BigDecimal getNegotiated() {
        return negotiated;
    }

    public void setNegotiated(BigDecimal negotiated) {
        this.negotiated = negotiated;
    }

    public BigDecimal getPaid() {
        return paid;
    }

    public void setPaid(BigDecimal paid) {
        this.paid = paid;
    }
}
