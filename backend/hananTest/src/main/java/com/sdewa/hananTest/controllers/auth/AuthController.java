package com.sdewa.hananTest.controllers.auth;

import com.sdewa.hananTest.dtos.request.LoginRequest;
import com.sdewa.hananTest.dtos.request.RefreshTokenRequest;
import com.sdewa.hananTest.dtos.request.SignupRequest;
import com.sdewa.hananTest.dtos.response.JwtResponse;
import com.sdewa.hananTest.dtos.response.CommonResponse;
import com.sdewa.hananTest.entity.User;
import com.sdewa.hananTest.services.AuthService;



import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    
    private final AuthService authService;
    
    @PostMapping("/signup")
    public ResponseEntity<JwtResponse> registerUser(@Valid @RequestBody SignupRequest signupRequest) {
        JwtResponse jwtResponse = authService.signup(signupRequest);
        return ResponseEntity.ok(jwtResponse);
    }
    
    @PostMapping("/login")
    public ResponseEntity<JwtResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        JwtResponse jwtResponse = authService.login(loginRequest);
        return ResponseEntity.ok(jwtResponse);
    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtResponse> refreshToken(@Valid @RequestBody RefreshTokenRequest request) {
        JwtResponse jwtResponse = authService.refreshToken(request.getRefreshToken());
        return ResponseEntity.ok(jwtResponse);
    }
    
    @SuppressWarnings("rawtypes")
    @DeleteMapping("/logout")
    public ResponseEntity<CommonResponse> logoutUser(Authentication authentication) {

        if (authentication != null && authentication.getPrincipal() instanceof User user) {
            authService.logout(user.getId());
            return ResponseEntity.ok(CommonResponse.of("Logged out successfully!"));
        }

        return ResponseEntity.badRequest()
                .body(CommonResponse.of("User not authenticated"));
    }
}
