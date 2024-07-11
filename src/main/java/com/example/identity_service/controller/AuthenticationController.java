package com.example.identity_service.controller;

import com.example.identity_service.commond.ResponseData;
import com.example.identity_service.dto.AuthTokenDTO;
import com.example.identity_service.dto.request.RefreshTokenRequest;
import com.example.identity_service.dto.request.UserLoginRequest;
import com.example.identity_service.dto.request.UserRegisterRequest;
import com.example.identity_service.dto.response.JwtResponse;
import com.example.identity_service.service.AuthenticationService;
import com.example.identity_service.utills.JwtUtils;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private JwtUtils jwtUtils;
    @PostMapping("/signup")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> register(@RequestBody UserRegisterRequest registerUserDto) {
        ResponseData<AuthTokenDTO> responseData = new ResponseData<>(HttpStatus.OK, "register_success", authenticationService.signup(registerUserDto));
        return ResponseEntity.ok(responseData);
    }

//    @PostMapping("/login")
////    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    public ResponseEntity<?> login(@RequestBody UserLoginRequest loginRequest){
//        ResponseData<AuthTokenDTO> responseData = new ResponseData<>(HttpStatus.OK, "login_success", authenticationService.authenticate(loginRequest));
//        return ResponseEntity.ok(responseData);
//    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequest request) {
        if (jwtUtils.validateJwtToken(request.getRefreshToken())) {
            if (jwtUtils.isTokenExpired(request.getRefreshToken())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Refresh token expired. Please log in again.");
            }

            String username = jwtUtils.getUsernameFromJwtToken(request.getRefreshToken());
            String newJwt = jwtUtils.generateToken(username);
            String newRefreshToken = jwtUtils.generateRefreshToken(username);

            return ResponseEntity.ok(new JwtResponse(newJwt, newRefreshToken));
        }
        return ResponseEntity.badRequest().body("Invalid refresh token");
    }
}
