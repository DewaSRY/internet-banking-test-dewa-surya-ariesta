package com.sdewa.hananTest.controllers.users;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sdewa.hananTest.dtos.common.UserProfileDto;

import com.sdewa.hananTest.entity.User;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UsersController {

    @GetMapping("/profile")
    public ResponseEntity<UserProfileDto> getUserProfile(
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(UserProfileDto.builder()
                .email(user.getEmail())
                .username(user.getUsername())
                .build());
    }

}
