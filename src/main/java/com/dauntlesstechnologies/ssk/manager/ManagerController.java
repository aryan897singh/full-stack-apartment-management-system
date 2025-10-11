package com.dauntlesstechnologies.ssk.manager;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/managers")
@CrossOrigin
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

    @PostMapping(path = "/create")
    public void createManager(@RequestBody UpdateManagerDto updateManagerDto){
        managerService.createManager(updateManagerDto);
    }

    @PutMapping(path = "/update/{id}")
    public void updateManager(@PathVariable("id") Long id, @RequestBody UpdateManagerDto updateManagerDto){
        managerService.updateManager(id, updateManagerDto);
    }

    @DeleteMapping(path = "/delete/{id}")
    public void deleteManager(@PathVariable("id") Long id){
        managerService.deleteManager(id);
    }

    /*
    C
    R - 1.Single Record -  DONE  2. All Records - DONE
    U
    D
     */
}
