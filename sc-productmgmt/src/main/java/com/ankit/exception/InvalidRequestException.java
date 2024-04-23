package com.ankit.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;

public class InvalidRequestException extends Exception{

    public InvalidRequestException(String message) {
        super(message);
    }

}
