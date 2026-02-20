package com.sdewa.hananTest.exception;

import lombok.Getter;

@Getter
public class CustomBadRequestException extends RuntimeException {

    private Integer code;

    public CustomBadRequestException(String message, Integer code) {
        super(message);
        this.code = code;
    }
}
