package com.dauntlesstechnologies.ssk.lease;

import com.dauntlesstechnologies.ssk.apartments.Apartment;
import com.dauntlesstechnologies.ssk.payment.Payment;
import com.dauntlesstechnologies.ssk.tenants.Tenant;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "lease_tbl")
public class Lease {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean is_active;

    private Date start;

    private Date end;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "apartment_id", nullable = false)
    private Apartment apartment;

    //We need to make Lease the owner of the many-to-many relationship with the Tenant entity
    @ManyToMany
    @JoinTable(
            name = "lease_tenants_tbl",
            joinColumns = @JoinColumn(name = "lease_id"), //This references back to this entity's PK
            inverseJoinColumns = @JoinColumn(name = "tenant_id") //This references the other entity's PK
    )
    private Set<Tenant> tenants; //(*) This is the variable that the tenants class annotation
                                 //is referencing

    @OneToMany(mappedBy = "lease",  fetch = FetchType.LAZY)
    private Set<Payment> payments;

    @Column(name = "rent_amount")
    private BigDecimal rentAmount;

    @Column(name = "maintenance_amount")
    private BigDecimal maintenanceAmount;

    @Column(name = "deposit_amount")
    private BigDecimal depositAmount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getIs_active() {
        return is_active;
    }

    public void setIs_active(Boolean is_active) {
        this.is_active = is_active;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public Apartment getApartment() {
        return apartment;
    }

    public void setApartment(Apartment apartment) {
        this.apartment = apartment;
    }

    public Set<Tenant> getTenants() {
        return tenants;
    }

    public void setTenants(Set<Tenant> tenants) {
        this.tenants = tenants;
    }

    public Set<Payment> getPayments() {
        return payments;
    }

    public void setPayments(Set<Payment> payments) {
        this.payments = payments;
    }

    public BigDecimal getRentAmount() {
        return rentAmount;
    }

    public void setRentAmount(BigDecimal rentAmount) {
        this.rentAmount = rentAmount;
    }

    public BigDecimal getMaintenanceAmount() {
        return maintenanceAmount;
    }

    public void setMaintenanceAmount(BigDecimal maintenanceAmount) {
        this.maintenanceAmount = maintenanceAmount;
    }

    public BigDecimal getDepositAmount() {
        return depositAmount;
    }

    public void setDepositAmount(BigDecimal depositAmount) {
        this.depositAmount = depositAmount;
    }
}
