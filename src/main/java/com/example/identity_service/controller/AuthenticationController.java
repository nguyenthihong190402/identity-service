package com.example.identity_service.controller;

import com.example.identity_service.dto.request.UserLoginRequest;
import com.example.identity_service.dto.request.UserRegisterRequest;
import com.example.identity_service.service.AuthenticationService;
import com.example.identity_service.utills.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthenticationController {
    @Autowired
    private final AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<?> register(@RequestBody UserRegisterRequest registerUserDto) {
        return authenticationService.signup(registerUserDto);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginRequest loginRequest){
        return ResponseEntity.ok(authenticationService.authenticate(loginRequest));
    }
}
