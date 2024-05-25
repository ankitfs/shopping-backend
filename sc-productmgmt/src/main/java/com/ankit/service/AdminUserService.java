package com.ankit.service;

import com.ankit.entity.user.AdminUserEntity;
import com.ankit.pojo.CommonResponsePojo;
import com.ankit.pojo.user.AdminUserSignUpRequest;

public interface AdminUserService{

    //Create a Admin User
    public AdminUserEntity createAdminUser(AdminUserSignUpRequest adminUserSignUpRequest) throws Exception;

    //TODO:: Update an Admin User
    public CommonResponsePojo updateAdminUser(AdminUserSignUpRequest adminUserSignUpRequest) throws Exception;

    //TODO:: Authenticate an Existing user
    public CommonResponsePojo authenticateAdminUser(AdminUserSignUpRequest adminUserSignUpRequest) throws Exception;
}
