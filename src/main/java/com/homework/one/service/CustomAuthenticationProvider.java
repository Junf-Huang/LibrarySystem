package com.homework.one.service;

import com.homework.one.service.CustomUserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final static Logger logger= LoggerFactory.getLogger(AuthenticationProvider.class);

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Override
    public Authentication authenticate(Authentication auth) throws AuthenticationException {

        //throw new BadCredentialsException("Wrong password.");
        logger.info("登录验证");
        final String username = auth.getName();
        final String password = auth.getCredentials().toString();
        logger.info("loginUsername={}",username);
        logger.info("loginPassword={}",password);

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

        logger.info("user={}",userDetails.toString());

        if (userDetails == null || !userDetails.getUsername().equalsIgnoreCase(username)) {
            throw new BadCredentialsException("Username not found.");
        }
        logger.info("databaseUsername={}",userDetails.getUsername());

        if (!password.equals(userDetails.getPassword())) {
            throw new BadCredentialsException("Wrong password.");
        }

        logger.info("databasePassword={}",userDetails.getPassword());
        Collection<? extends GrantedAuthority>  roles = userDetails.getAuthorities();

        return new UsernamePasswordAuthenticationToken(username, password, roles);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
