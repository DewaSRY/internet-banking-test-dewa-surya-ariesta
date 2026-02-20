package com.sdewa.hananTest.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sdewa.hananTest.dtos.common.CustomErrorResponse;
import com.sdewa.hananTest.exception.CustomBadRequestException;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<CustomErrorResponse> handleRuntime(RuntimeException ex) {

        return new ResponseEntity<>(
                CustomErrorResponse.builder().status(HttpStatus.BAD_REQUEST.value())
                        .message(ex.getMessage())
                        .timestamp(LocalDateTime.now()).build(),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CustomBadRequestException.class)
    public ResponseEntity<CustomErrorResponse> handleCustomerBadRequest(CustomBadRequestException ex) {

        return new ResponseEntity<>(
                CustomErrorResponse.builder()
                        .status(ex.getCode())
                        .message(ex.getMessage())
                        .timestamp(LocalDateTime.now()).build(),
                HttpStatus.BAD_REQUEST);
    }
}
