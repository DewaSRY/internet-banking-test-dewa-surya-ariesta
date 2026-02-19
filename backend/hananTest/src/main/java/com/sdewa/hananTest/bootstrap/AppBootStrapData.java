package com.sdewa.hananTest.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.sdewa.hananTest.dtos.request.SignupRequest;
import com.sdewa.hananTest.services.AuthService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AppBootStrapData implements CommandLineRunner {

    private final AuthService authService;

    @Override
    public void run(String... args) throws Exception {
        authService.signup(SignupRequest.builder()
                .email("user1@example.com")
                .password("password123")
                .username("user 1")
                .build());

        authService.signup(SignupRequest.builder()
                .email("user2@example.com")
                .password("password123")
                .username("user 2")
                .build());
    }

}
