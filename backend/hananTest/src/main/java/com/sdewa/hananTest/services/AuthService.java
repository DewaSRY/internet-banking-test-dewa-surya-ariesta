package com.sdewa.hananTest.services;

import com.sdewa.hananTest.dtos.request.LoginRequest;
import com.sdewa.hananTest.dtos.request.SignupRequest;
import com.sdewa.hananTest.dtos.response.JwtResponse;

public interface AuthService {
    JwtResponse login(LoginRequest loginRequest);
    JwtResponse signup(SignupRequest signupRequest);
    JwtResponse refreshToken(String refreshToken);
    void logout(Long userId);

}
