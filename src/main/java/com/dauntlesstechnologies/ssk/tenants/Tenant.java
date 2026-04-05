package com.dauntlesstechnologies.ssk.tenants;

import com.dauntlesstechnologies.ssk.lease.Lease;
import jakarta.persistence.*;
import org.hibernate.annotations.SQLRestriction;

import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "tenants_tbl")
//ALL BASIC JPA REPO METHODS WILL ONLY WORK WITH THE WHERE CLAUSE, SO IT DOESN'T WORK WITH SOFT
//DELETED TENANTS
//NOTE: WHERE IS NOW SQLRESTRICTION IN MODERN HIBERNATE!!
@SQLRestriction("exists_flag = true")
//NOTE - EXISTS IS A KEYWORD IN SQL SO HAD TO RENAME THE COLUMN TO EXISTS_FLAG
public class Tenant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column
    private String address;

    @Column(name = "father_name")
    private String fatherName;

    @Column(unique = true, name = "unique_identifier")
    private String uniqueIdentifier;

    @Column(name = "background_check")
    private boolean isBackgroundChecked;

    @ManyToMany(mappedBy = "tenants", fetch = FetchType.LAZY) // (*) this refers to the variable defined in lease class
    private Set<Lease> leases;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getUniqueIdentifier() {
        return uniqueIdentifier;
    }

    public void setUniqueIdentifier(String uniqueIdentifier) {
        this.uniqueIdentifier = uniqueIdentifier;
    }

    public boolean isBackgroundChecked() {
        return isBackgroundChecked;
    }

    public void setBackgroundChecked(boolean backgroundChecked) {
        isBackgroundChecked = backgroundChecked;
    }

    public Set<Lease> getLeases() {
        return leases;
    }

    public void setLeases(Set<Lease> leases) {
        this.leases = leases;
    }
}
