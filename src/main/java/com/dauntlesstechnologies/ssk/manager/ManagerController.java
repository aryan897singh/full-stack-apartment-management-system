package com.dauntlesstechnologies.ssk.manager;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/managers")
public class ManagerController {

    private final ManagerService managerService;

    public ManagerController(ManagerService managerService) {
        this.managerService = managerService;
    }

    @GetMapping(path = "/{id}")
    public ManagerDto getTenantById(@PathVariable("id") Long id){
       return managerService.findManagerById(id);
    }

    @GetMapping(path = "/getAll")
    public List<ManagerDto> getAll(){
        return managerService.findAllManagers();
    }

    /*
    C
    R - 1.Single Record -  DONE  2. All Records - DONE
    U
    D
     */
}
