package com.ankit.pojo.user;

import com.ankit.pojo.CommonResponsePojo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse extends CommonResponsePojo {

    private String userName;
    private String token;

}
