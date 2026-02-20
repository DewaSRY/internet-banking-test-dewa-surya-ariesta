package com.sdewa.hananTest.dtos.common;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomErrorResponse {
    private int status;
    private String message;
    private LocalDateTime timestamp;
}
