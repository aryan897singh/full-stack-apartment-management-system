package com.dauntlesstechnologies.ssk.configuration;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/configuration")
@CrossOrigin
public class ConfigurationController {

    private ConfigurationService configurationService;

    public ConfigurationController(ConfigurationService configurationService) {
        this.configurationService = configurationService;
    }

    @GetMapping
    public List<ConfigurationDto> getAllConfigSettings(){
        return configurationService.getAllConfigSettings();
    }

    @PutMapping("/update")
    public void updateSetting(@RequestBody UpdateConfigurationDto dto) {
        configurationService.updateSetting(dto);
    }

    /*
    C
    R
    U
    D
     */

}
