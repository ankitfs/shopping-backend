package com.ankit.pojo.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminUserPojo {

    private String userName;
    private String password;
    private String firstName;
    private String lastName;
}
