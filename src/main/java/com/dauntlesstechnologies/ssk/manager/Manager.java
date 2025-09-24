package com.dauntlesstechnologies.ssk.manager;

import com.dauntlesstechnologies.ssk.maintenance_requests.MaintenanceType;
import jakarta.persistence.*;

import java.util.Set;

//These managers are basically specific people who take care of the maintenance
@Entity
@Table(name = "managers_tbl")
public class Manager {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String Name;

    @Column
    private Long number;

    // This is the new field that will link to the maintenance types
    @ElementCollection(targetClass = MaintenanceType.class)
    @CollectionTable(name = "manager_maintenance_types", joinColumns = @JoinColumn(name = "manager_id"))
    @Column(name = "maintenance_type", nullable = false)
    @Enumerated(EnumType.STRING) // Store enum names as strings in the database
    private Set<MaintenanceType> maintenanceTypes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public Set<MaintenanceType> getMaintenanceTypes() {
        return maintenanceTypes;
    }

    public void setMaintenanceTypes(Set<MaintenanceType> maintenanceTypes) {
        this.maintenanceTypes = maintenanceTypes;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
