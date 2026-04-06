package com.dauntlesstechnologies.ssk.manager;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/managers")
@CrossOrigin
public class ManagerController {

    private final ManagerService managerService;

    public ManagerController(ManagerService managerService) {
        this.managerService = managerService;
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<ManagerDto> getManagerById(@PathVariable("id") Long id){ // Fixed typo here
        return ResponseEntity.ok(managerService.findManagerById(id));
    }

    @GetMapping
    public ResponseEntity<List<ManagerDto>> getAllManagers(){
        return ResponseEntity.ok(managerService.findAllManagers());
    }

    @PostMapping
    public ResponseEntity<ManagerDto> createManager(@RequestBody UpdateManagerDto updateManagerDto){
        return new ResponseEntity<>(managerService.createManager(updateManagerDto), HttpStatus.CREATED);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<Void> updateManager(@PathVariable("id") Long id, @RequestBody UpdateManagerDto updateManagerDto){
        managerService.updateManager(id, updateManagerDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteManager(@PathVariable("id") Long id){
        managerService.deleteManager(id);
        return ResponseEntity.noContent().build();
    }
}
