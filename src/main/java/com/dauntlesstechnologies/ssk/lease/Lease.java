package com.dauntlesstechnologies.ssk.lease;

import com.dauntlesstechnologies.ssk.apartments.Apartment;
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

    @ManyToOne
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


    @Column(name = "rent_amount")
    private BigDecimal rentAmount;

    @Column(name = "maintenance_amount")
    private BigDecimal maintenanceAmount;

    @Column(name = "deposit_amount")
    private BigDecimal depositAmount;




}
