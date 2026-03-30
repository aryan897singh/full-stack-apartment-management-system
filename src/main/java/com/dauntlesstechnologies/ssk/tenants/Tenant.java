package com.dauntlesstechnologies.ssk.tenants;

import com.dauntlesstechnologies.ssk.apartments.Apartment;
import com.dauntlesstechnologies.ssk.lease.Lease;
import jakarta.persistence.*;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.beans.factory.annotation.Qualifier;

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

    @Column(name = "main_owner")
    private Boolean mainOwner;

    @Column
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column
    private String address;

    @Column(name = "father_name")
    private String fatherName;

    @ManyToOne
    @JoinColumn(name = "apartment_id", referencedColumnName = "id") //nullable = false, because we want to soft delete)
    private Apartment apartment;

    @Column(unique = true, name = "aadhar_card_number")
    private String aadharCardNumber;

    @Column(name = "criminal_history")
    private boolean criminalHistory;

    @Column(name = "agreement_signed")
    private boolean agreementSigned;

    @Column(name = "join_date")
    private Date joinDate;

    @Column(name = "leave_date")
    private Date leaveDate;

    @Column(name = "exists_flag")
    private boolean exists;

    @ManyToMany(mappedBy = "tenants") // (*) this refers to the variable defined in lease class
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

    public Apartment getApartment() {
        return apartment;
    }

    public void setApartment(Apartment apartment) {
        this.apartment = apartment;
    }

    public String getAadharCardNumber() {
        return aadharCardNumber;
    }

    public void setAadharCardNumber(String aadharCardNumber) {
        this.aadharCardNumber = aadharCardNumber;
    }

    public boolean isCriminalHistory() {
        return criminalHistory;
    }

    public void setCriminalHistory(boolean criminalHistory) {
        this.criminalHistory = criminalHistory;
    }

    public boolean isAgreementSigned() {
        return agreementSigned;
    }

    public void setAgreementSigned(boolean agreementSigned) {
        this.agreementSigned = agreementSigned;
    }

    public Date getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(Date joinDate) {
        this.joinDate = joinDate;
    }

    public Date getLeaveDate() {
        return leaveDate;
    }

    public void setLeaveDate(Date leaveDate) {
        this.leaveDate = leaveDate;
    }

    public boolean getExists() {
        return exists;
    }

    public void setExists(boolean exists) {
        this.exists = exists;
    }

    public Boolean isMainOwner() {
        return mainOwner;
    }

    public void setMainOwner(Boolean mainOwner) {
        this.mainOwner = mainOwner;
    }
}
