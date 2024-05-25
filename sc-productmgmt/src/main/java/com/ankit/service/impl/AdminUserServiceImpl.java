package com.ankit.service.impl;

import com.ankit.dao.AdminUserRepository;
import com.ankit.entity.user.AdminUserEntity;
import com.ankit.pojo.CommonResponsePojo;
import com.ankit.pojo.user.AdminUserSignUpRequest;
import com.ankit.service.AdminUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service
public class AdminUserServiceImpl implements AdminUserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AdminUserRepository adminUserRepository;

    @Override
    public AdminUserEntity createAdminUser(AdminUserSignUpRequest adminUserSignUpRequest) throws Exception {

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
