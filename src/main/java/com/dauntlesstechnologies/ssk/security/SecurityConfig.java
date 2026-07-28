package com.dauntlesstechnologies.ssk.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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
                        auth
                                //Note: The input of requestMatchers is just the path (string) being requested
                                //does not matter if it is an API endpoint or an HTML page

                                //INFINITE LOOP ERROR ENCOUNTERED! ALLOW EVERYONE TO ACCESS UNAUTHORIZED REDIRECT
                                .requestMatchers("/access-denied").permitAll()

                                //Problem - API endpoints locked, page wide open
                                //Solution - Lock both the HTML page and the data endpoints (Defense in depth)
                                .requestMatchers("/owner_pages/**").hasRole("OWNER")

                                .requestMatchers("/api/apartments/**").hasRole("OWNER")
                                .requestMatchers("/api/tenants/**").hasRole("OWNER")
                                .requestMatchers("/configuration/**").hasRole("OWNER")
                                .requestMatchers("/api/furniture/**").hasRole("OWNER")
                                .requestMatchers("/api/maintenanceRequests/**").hasRole("OWNER")
                                .requestMatchers("/api/managers/**").hasRole("OWNER")
                                .requestMatchers("/api/payments/**").hasRole("OWNER")
								.requestMatchers("/api/leases/**").hasRole("OWNER")





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
		http.csrf(csrf->csrf.disable());
        return http.build(); //Return the rulebook we defined

    }

}
