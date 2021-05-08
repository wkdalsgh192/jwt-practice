 package com.caterpie.timeletter.service;


import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.caterpie.timeletter.entity.User;
import com.caterpie.timeletter.repository.UserRepository;

 @Component("userDetailsService")
 public class CustomUserDetailsService implements UserDetailsService {
    
	 private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);
	 
	 private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
       this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String username) {
       return userRepository.findOneWithAuthoritiesByEmail(username)
          .map(user -> createUser(username, user))
          .orElseThrow(() -> new UsernameNotFoundException(username + " -> 데이터베이스에서 찾을 수 없습니다."));
    }

    private org.springframework.security.core.userdetails.User createUser(String username, User user) {
       if (!user.isActivated()) {
          throw new RuntimeException(username + " -> 활성화되어 있지 않습니다.");
       }
       logger.info("{}",user.toString());
       List<GrantedAuthority> grantedAuthorities = user.getAuthorities().stream()
               .map(authority -> new SimpleGrantedAuthority(authority.getAuthorityName()))
               .collect(Collectors.toList());
       return new org.springframework.security.core.userdetails.User(user.getEmail(),
               user.getPassword(),
               grantedAuthorities);
    }
 }