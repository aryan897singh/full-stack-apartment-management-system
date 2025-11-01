package com.dauntlesstechnologies.ssk.configuration;

public record ConfigurationDto(
        Long id,
        String configKey,
        String configValue) {
}
