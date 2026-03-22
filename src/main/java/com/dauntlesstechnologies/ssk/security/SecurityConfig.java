package com.dauntlesstechnologies.ssk.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final EntraIdRoleConverter entraIdRoleConverter;

    public SecurityConfig(EntraIdRoleConverter entraIdRoleConverter) { //constructor injection
        this.entraIdRoleConverter = entraIdRoleConverter;
    }

    //DEFINING THE SECURITY FILTER CHAIN:
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth->
                        auth //Every URl is locked, you MUST be authenticated
                                .anyRequest()
                                .authenticated())
                .oauth2Login(oauth2 ->
                        //If not authenticated, go through the oauth2 login flow
                        oauth2
                                //the userInfoEndpoint allows use to configure the OIDC user info endpoint
                                //to customize what attributes are retrieved and how they are mapped
                                //once the user is authenticated
                                //It returns a UserInfoEndpointConfig object
                                .userInfoEndpoint(userInfo ->
                                        userInfo
                                                //Use our custom translator during OIDC login
                                                .oidcUserService(entraIdRoleConverter))
                                                //takes OidcUser and returns UserEndpointConfig object

                );
        return http.build(); //Return the rulebook we defined

    }

}
