package com.ankit.service.impl;

import com.ankit.dao.AdminUserRepository;
import com.ankit.entity.user.AdminUserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private AdminUserRepository adminUserRepository;

    private static final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<AdminUserEntity> adminUserEntity = adminUserRepository.findByUsername(username);

        logger.info("UserInfo Details from DB, {}",adminUserEntity.get());

        return User.builder()
               .username(username)
               .password(adminUserEntity.get().getPassword())
               .build();
    }
}
