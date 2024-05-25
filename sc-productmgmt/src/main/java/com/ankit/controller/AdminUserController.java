package com.ankit.controller;

import com.ankit.entity.user.AdminUserEntity;
import com.ankit.pojo.CommonResponsePojo;
import com.ankit.pojo.user.AdminUserSignUpRequest;
import com.ankit.service.AdminUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminUserController {

    @Autowired
    private AdminUserService adminUserService;

    @PostMapping("/create")
    public CommonResponsePojo createAdminUser(@RequestBody AdminUserSignUpRequest adminUserSignUpRequest) {

        CommonResponsePojo response = new CommonResponsePojo();

        try {
            AdminUserEntity adminUserEntity = adminUserService.createAdminUser(adminUserSignUpRequest);
            response.setStatus(true);
            response.setReturnCode(201);
            response.setMessage("Admin User has been created");
        }
        catch (Exception ex) {
            response.setStatus(false);
            response.setReturnCode(500);
            response.setMessage("Server Error");
        }

          return response;
    }

    //TODO:: LOGIN ADMIN USER
}
