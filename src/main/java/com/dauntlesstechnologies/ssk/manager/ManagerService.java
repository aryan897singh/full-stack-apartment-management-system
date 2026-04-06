package com.dauntlesstechnologies.ssk.manager;

import jakarta.transaction.Transactional;
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
            managerDtos.add(entityToDto(manager));
        }
        return managerDtos;
    }

    @Transactional
    public ManagerDto createManager(UpdateManagerDto updateManagerDto){
        Manager manager = new Manager();

        manager.setName(updateManagerDto.name());
        manager.setNumber(updateManagerDto.number());
        manager.setMaintenanceTypes(updateManagerDto.maintenanceTypes());

        managerRepository.save(manager);
        return entityToDto(manager);
    }

    @Transactional
    public void updateManager(Long id, UpdateManagerDto updateManagerDto){

        Optional<Manager> managerOptional = managerRepository.findById(id);

        if(managerOptional.isPresent()){
            Manager manager = managerOptional.get();
            manager.setName(updateManagerDto.name());
            manager.setNumber(updateManagerDto.number());
            manager.setMaintenanceTypes(updateManagerDto.maintenanceTypes());
            managerRepository.save(manager);
        }else{
            throw new RuntimeException("No such manager found with the id provided");
        }
    }

    @Transactional
    public void deleteManager(Long id){
        Optional<Manager> managerOptional = managerRepository.findById(id);

        if(managerOptional.isPresent()){
            managerRepository.delete(managerOptional.get());
        }else{
            throw new RuntimeException("No such manager found with the id provided");
        }
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
