package com.dauntlesstechnologies.ssk.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import java.nio.file.AccessDeniedException;

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
                        auth
                                /*
                                TESTING: Dashboard.html can only be accessed by owner
                                         Tenants.html can only be accessed by tenant
                                 */
                                .requestMatchers("/apartments/**").hasRole("OWNER")
                                .requestMatchers("/tenants/**").hasRole("TENANT")

                                //INFINITE LOOP ERROR ENCOUNTERED! ALLOW EVERYONE TO ACCESS UNAUTHORIZED REDIRECT
                                .requestMatchers("/access-denied").permitAll()


                                .anyRequest()
                                .authenticated()
                )
                //This handles unauthorized cases and sends to the correct page
                .exceptionHandling(ex ->
                        ex
                                .accessDeniedHandler((req, resp, exc) -> {
                                    resp.sendRedirect("/access-denied");
                                }))
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
