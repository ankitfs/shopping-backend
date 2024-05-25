package com.ankit.service;

import com.ankit.entity.user.AdminUserEntity;
import com.ankit.pojo.CommonResponsePojo;
import com.ankit.pojo.user.AdminUserPojo;

public interface AdminUserService{

    //Create a Admin User
    public AdminUserEntity createAdminUser(AdminUserPojo adminUserPojo) throws Exception;

    //TODO:: Update an Admin User
    public CommonResponsePojo updateAdminUser(AdminUserPojo adminUserPojo) throws Exception;

    //TODO:: Authenticate an Existing user
    public CommonResponsePojo authenticateAdminUser(AdminUserPojo adminUserPojo) throws Exception;
}
