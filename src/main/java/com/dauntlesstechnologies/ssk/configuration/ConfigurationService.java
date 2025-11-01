package com.dauntlesstechnologies.ssk.configuration;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ConfigurationService {

    private ConfigurationRepository configurationRepository;

    public ConfigurationService(ConfigurationRepository configurationRepository) {
        this.configurationRepository = configurationRepository;
    }

    public ConfigurationDto entityToDto(Configuration configuration){
        return new ConfigurationDto(
                configuration.getId(),
                configuration.getConfigKey(),
                configuration.getConfigValue()
        );
    }

    public List<ConfigurationDto> getAllConfigSettings(){
        List<Configuration>  configurations = configurationRepository.findAll();
        List<ConfigurationDto> configurationDtos = new ArrayList<>();

        for(Configuration configuration : configurations){
            configurationDtos.add(entityToDto(configuration));
        }
        return configurationDtos;
    }

    @Transactional
    public void updateSetting(UpdateConfigurationDto dto){
        Optional<Configuration> configOptional = configurationRepository.findByConfigKey(dto.configKey());
        if(configOptional.isPresent()){
            Configuration config = configOptional.get();

            config.setConfigValue(dto.configValue());

            configurationRepository.save(config);

        }else{
            throw new EntityNotFoundException("Configuration not found");
        }
    }

    /*
    R-
    U-
     */
}
