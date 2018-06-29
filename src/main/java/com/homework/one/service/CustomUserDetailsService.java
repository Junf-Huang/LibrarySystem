package com.homework.one.service;

import com.homework.one.bean.CustomUserDetails;
import com.homework.one.bean.User;
import com.homework.one.responsitory.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    private final static Logger logger= LoggerFactory.getLogger(CustomUserDetailsService.class);

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {

        logger.info("获取用户信息");

        Optional<User> optionalUser = userRepository.findByStuID(userId);

        optionalUser
                .orElseThrow(() -> new UsernameNotFoundException("Username not found1"));

/*
        return optionalUser
                .map(user ->
                     new CustomUserDetails(user)).get();
         */

        return optionalUser
                .map(CustomUserDetails::new).get();
      }
}
