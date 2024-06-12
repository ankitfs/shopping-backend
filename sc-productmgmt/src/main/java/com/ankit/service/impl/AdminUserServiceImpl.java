package com.ankit.service.impl;

import com.ankit.dao.AdminUserRepository;
import com.ankit.entity.user.AdminUserEntity;
import com.ankit.exception.InvalidRequestException;
import com.ankit.pojo.CommonResponsePojo;
import com.ankit.pojo.user.AdminUserSignUpRequest;
import com.ankit.service.AdminUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AdminUserServiceImpl implements AdminUserService {

    private Logger logger = LoggerFactory.getLogger(AdminUserServiceImpl.class);

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AdminUserRepository adminUserRepository;

    @Override
    public AdminUserEntity createAdminUser(AdminUserSignUpRequest adminUserSignUpRequest) throws Exception {

        logger.info("Entered");

        Optional<AdminUserEntity> checkAdminUser =  adminUserRepository.findByUsername(adminUserSignUpRequest.getUserName());
        if(checkAdminUser.isPresent()) {
            throw new InvalidRequestException("User Already Exists");
        }
        CommonResponsePojo commonResponsePojo = new CommonResponsePojo();
        AdminUserEntity adminUserEntity = new AdminUserEntity();
        adminUserEntity.setFirstName(adminUserSignUpRequest.getFirstName());
        adminUserEntity.setLastName(adminUserSignUpRequest.getLastName());
        adminUserEntity.setUsername(adminUserSignUpRequest.getUserName());

        adminUserEntity.setPassword(passwordEncoder.encode(adminUserSignUpRequest.getPassword()));
        adminUserEntity.setCreatedDate(Timestamp.valueOf(LocalDateTime.now()));

        adminUserEntity = adminUserRepository.save(adminUserEntity);

        return adminUserEntity;
    }

    @Override
    public CommonResponsePojo updateAdminUser(AdminUserSignUpRequest adminUserSignUpRequest) throws Exception {
        return null;
    }

    @Override
    public CommonResponsePojo authenticateAdminUser(AdminUserSignUpRequest adminUserSignUpRequest) throws Exception {
        return null;
    }
}
