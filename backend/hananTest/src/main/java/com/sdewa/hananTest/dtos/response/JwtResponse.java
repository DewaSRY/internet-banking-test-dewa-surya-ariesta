package com.sdewa.hananTest.dtos.response;

import com.sdewa.hananTest.dtos.common.UserProfileDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JwtResponse {
    private String accessToken;
    private String refreshToken;
    private UserProfileDto userProfileDto;
}
