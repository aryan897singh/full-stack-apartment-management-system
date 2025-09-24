package com.dauntlesstechnologies.ssk.manager;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ManagerService {

    private final ManagerRepository managerRepository;

    public ManagerService(ManagerRepository managerRepository) {
        this.managerRepository = managerRepository;
    }

    public ManagerDto findManagerById(Long id) {
        Optional<Manager> managerOptional = managerRepository.findById(id);

        if(managerOptional.isPresent()){
            return entityToDto(managerOptional.get());
        }
        else{
            throw new RuntimeException("No such manager found with the id provided");
        }
    }

    public List<ManagerDto> findAllManagers(){
        List<Manager> managers = managerRepository.findAll();
        List<ManagerDto> managerDtos = new ArrayList<>();
        for(Manager manager : managers){
            entityToDto(manager);
            managerDtos.add(entityToDto(manager));
        }
        return managerDtos;
    }

    public ManagerDto entityToDto(Manager manager){

        return new ManagerDto(
                manager.getId(),
                manager.getName(),
                manager.getNumber(),
                manager.getMaintenanceTypes()

        );

    }
}
