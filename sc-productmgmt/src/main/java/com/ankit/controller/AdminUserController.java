package com.ankit.controller;

import com.ankit.configuration.JwtHelper;
import com.ankit.entity.user.AdminUserEntity;
import com.ankit.pojo.CommonResponsePojo;
import com.ankit.pojo.user.AdminUserSignUpRequest;
import com.ankit.pojo.user.LoginRequest;
import com.ankit.pojo.user.LoginResponse;
import com.ankit.service.AdminUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminUserController {

    private static final Logger logger = LoggerFactory.getLogger(AdminUserController.class);

    @Autowired
    private AdminUserService adminUserService;

    @Autowired
    private AuthenticationManager authenticationManager;

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

    @PostMapping("/login")
    public LoginResponse loginUser(@RequestBody LoginRequest loginRequest) {

        LoginResponse loginResponse = new LoginResponse();

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
            String token = JwtHelper.generateToken(loginRequest.getUsername());
            logger.info("user token {}", token);
            loginResponse.setUserName(loginRequest.getUsername());
            loginResponse.setToken(token);
            loginResponse.setStatus(true);
            loginResponse.setReturnCode(200);
        }
        catch (Exception ex ) {
            ex.printStackTrace();
            logger.error("error on login {}",ex.getMessage());
            loginResponse.setStatus(false);
            loginResponse.setReturnCode(500);
        }
        return loginResponse;
    }
}
