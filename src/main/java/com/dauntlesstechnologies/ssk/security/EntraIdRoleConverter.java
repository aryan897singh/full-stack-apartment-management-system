package com.dauntlesstechnologies.ssk.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service //Stereotype Annotation
//This tells Spring, hey we have certain objects here you can use to inject to bean dependencies
//via IoC to obtain the beans
public class EntraIdRoleConverter extends OidcUserService {

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException{

        //Since we are overriding the method, we call the super method to let spring do
        //it's default verification of the Microsoft signature and to create the base user
        //which is the OidcUser object from the OidcUserRequest
        OidcUser oidcUser = super.loadUser(userRequest);

        //We then grab the roles from the JWT token (The OidcUser was created from the JWT token by
        //the SecurityFilterChain
        List<String> entraRoles = oidcUser.getClaimAsStringList("roles");

        //Safety: In case the user has no defined roles, just return the standard OidcUser Object
        if(entraRoles == null || entraRoles.isEmpty()){
            return oidcUser;
        }

        //Using Streams, we then map out the roles and replace them and save them as a list,
        //and convert them into GrantedAuthority objects
        List<GrantedAuthority> mappedAuthorities = entraRoles
                .stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toList());

        //Problem - The user has default badges like SCOPE_email and SCOPE_profile
        //which we don't want to replace and remove, so we must add them with what we have
        Collection<GrantedAuthority> allAuthorities = Stream
                .concat(
                        oidcUser.getAuthorities().stream(), //what the user has
                        mappedAuthorities.stream() //what we modified
                ).collect(Collectors.toList()); //Combine them

        //finally, return a new user object with the combined authorities
        return new DefaultOidcUser(allAuthorities, oidcUser.getIdToken(), oidcUser.getUserInfo());

        //IdToken of OidcUser object - Digitally signed Security Token in JWT format - PROOF OF USER AUTHENTICATION
        //userInfo is a more detailed profile overview

        //NOTE: We query the db via the user info from the jwt token and oidcUser,
        //therefore if we just returned a default user with the right authorities, the system
        //would not know what information to return from the db, because we don't know who it is

    }

}
