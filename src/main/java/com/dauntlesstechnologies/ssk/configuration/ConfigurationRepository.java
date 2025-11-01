package com.dauntlesstechnologies.ssk.configuration;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ConfigurationRepository extends JpaRepository<Configuration, Long> {

    public Optional<Configuration> findByConfigKey(String configKey);

}
