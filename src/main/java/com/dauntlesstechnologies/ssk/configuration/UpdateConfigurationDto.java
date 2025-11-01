package com.dauntlesstechnologies.ssk.configuration;

public record UpdateConfigurationDto(
        String configKey,
        String configValue
) {
}
