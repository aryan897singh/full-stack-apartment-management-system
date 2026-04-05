package com.dauntlesstechnologies.ssk.tenants;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(path = "/api/tenants")
@CrossOrigin
public class TenantController {

    private final TenantService tenantService;

    public TenantController(TenantService tenantService){
        this.tenantService = tenantService;
    }

    @GetMapping
    public ResponseEntity<List<TenantDto>> getTenantByName(@RequestParam("name") String name){
        return ResponseEntity.ok(tenantService.createAndSearchTenantRecord(name));
    }

    @GetMapping("/{tenantId}")
    //FIRST STEP IN UPDATING DATA IS TO DISPLAY THE DATA
    public ResponseEntity<TenantDto> getTenantById(@PathVariable("tenantId") Long id){
        return ResponseEntity.ok(tenantService.findById(id));
    }


    @PutMapping("/{id}")
    //SECOND STEP IN UPDATING DATA IS TAKING MODIFIED DATA AND UPDATING IT
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody UpdateTenantDto updateTenantDto){
        tenantService.updateTenant(updateTenantDto, id);
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<TenantDto> create(@RequestBody UpdateTenantDto updateTenantDto){
        TenantDto newTenantDto = tenantService.createTenant(updateTenantDto);
        return new ResponseEntity<>(newTenantDto, HttpStatus.CREATED);
    }

}
