package com.sdewa.hananTest.services.impl;

import com.sdewa.hananTest.dtos.common.UserProfileDto;
import com.sdewa.hananTest.dtos.request.LoginRequest;
import com.sdewa.hananTest.dtos.request.SignupRequest;
import com.sdewa.hananTest.dtos.response.JwtResponse;
import com.sdewa.hananTest.entity.RefreshToken;
import com.sdewa.hananTest.entity.User;
import com.sdewa.hananTest.repository.UserRepository;
import com.sdewa.hananTest.services.AuthService;
import com.sdewa.hananTest.services.RefreshTokenService;
import com.sdewa.hananTest.utils.JwtUtil;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final RefreshTokenService refreshTokenService;
    
    @Override
    @Transactional
    public JwtResponse login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );
        
        SecurityContextHolder.getContext().setAuthentication(authentication);
        
        User user = (User) authentication.getPrincipal();
        String jwt = jwtUtil.generateToken(user);
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.getId());
        
        return JwtResponse.builder()
                .accessToken(jwt)
                .refreshToken(refreshToken.getToken())
                .userProfileDto(UserProfileDto.builder()
                        .email(user.getEmail())
                        .username(user.getUsername())
                        .build())
                .build();
    }
    
    @Override
    @Transactional
    public JwtResponse signup(SignupRequest signupRequest) {

        if (userRepository.existsByUsername(signupRequest.getUsername())) {
            throw new RuntimeException("Error: Username is already taken!");
        }
        
        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            throw new RuntimeException("Error: Email is already in use!");
        }
        
        User user = User.builder()
                .username(signupRequest.getUsername())
                .email(signupRequest.getEmail())
                .password(passwordEncoder.encode(signupRequest.getPassword()))
                .build();

        userRepository.save(user);

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getEmail(),
                        signupRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        

        String jwt = jwtUtil.generateToken(user);
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.getId());

        return JwtResponse.builder()
                .accessToken(jwt)
                .refreshToken(refreshToken.getToken())
                .userProfileDto(UserProfileDto.builder()
                        .email(user.getEmail())
                        .username(user.getUsername())
                        .build())
                .build();
    }

    
    @Override
    @Transactional
    public JwtResponse refreshToken(String refreshToken) {
        return refreshTokenService.findByToken(refreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String jwt = jwtUtil.generateToken(user);
                    return JwtResponse.builder()
                            .accessToken(jwt)
                            .refreshToken(refreshToken)
                            .userProfileDto(UserProfileDto.builder()
                                    .email(user.getEmail())
                                    .username(user.getUsername())
                                    .build())
                            .build();
                })
                .orElseThrow(() -> new RuntimeException("Refresh token not found!"));
    }
    
    @Override
    @Transactional
    public void logout(Long userId) {
        refreshTokenService.deleteByUserId(userId);
    }

}
