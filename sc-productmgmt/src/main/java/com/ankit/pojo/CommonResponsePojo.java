package com.ankit.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonResponsePojo {

    private boolean status;
    private int returnCode;
    private String errorMessage;
}
