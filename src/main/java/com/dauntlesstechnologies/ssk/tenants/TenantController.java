package com.dauntlesstechnologies.ssk.tenants;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(path = "/tenants")
public class TenantController {

    private final TenantService tenantService;

    public TenantController(TenantService tenantService){
        this.tenantService = tenantService;
    }

    @GetMapping
    public List<TenantDto> getTenantByName(@RequestParam("name") String name){
        return tenantService.createAndSearchTenantRecord(name);
    }

    @GetMapping("/{id}")
    //FIRST STEP IN UPDATING DATA IS TO DISPLAY THE DATA
    public TenantDto getTenantById(@PathVariable("id") Long id){
        return tenantService.findById(id);
    }

    @PutMapping("/update/{id}")
    //SECOND STEP IN UPDATING DATA IS TAKING MODIFIED DATA AND UPDATING IT
    public void update(@PathVariable Long id, @RequestBody UpdateTenantDto updateTenantDto){
        tenantService.updateTenant(updateTenantDto, id);
    }

    @PostMapping("/create")
    public void create(@RequestBody UpdateTenantDto updateTenantDto){
        tenantService.createTenant(updateTenantDto);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable("id") Long id){
        tenantService.deleteTenant(id);

    }

    /*
    C - DONE
    R - DONE
    U - DONE
    D - DONE
     */
}
