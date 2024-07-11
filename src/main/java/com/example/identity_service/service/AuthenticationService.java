package com.example.identity_service.service;

import com.example.identity_service.dto.AuthTokenDTO;
import com.example.identity_service.dto.request.UserLoginRequest;
import com.example.identity_service.dto.request.UserRegisterRequest;
import com.example.identity_service.dto.response.JwtResponse;
import com.example.identity_service.entity.RoleEntity;
import com.example.identity_service.entity.UserEntity;
import com.example.identity_service.repository.RoleRepository;
import com.example.identity_service.repository.UserRepository;
import com.example.identity_service.utills.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
//@RequiredArgsConstructor
//@AllArgsConstructor
public class AuthenticationService {
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private final AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtils jwtUtils;
//    @Autowired
//    private RoleRepository roleRepository;

    public AuthenticationService(
            UserRepository userRepository,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public AuthTokenDTO signup(UserRegisterRequest input) {
        UserEntity user = new UserEntity();
//        RoleEntity userRole = roleRepository.findByName("USER");
//        if (userRole == null) {
//            throw new RuntimeException("Role USER not found");
//        }
//
//        Set<RoleEntity> roles = new HashSet<>();
//        roles.add(userRole);
//        user.setRoles(roles);
        user.setStatus((byte) 1);
        user.setFullName(input.getFullName());
        user.setUsername(input.getUsername());
        user.setPassword(passwordEncoder.encode(input.getPassword()));
        final String token = jwtUtils.generateToken(input.getUsername());
        userRepository.save(user);
        return new AuthTokenDTO(token);
    }

    public ResponseEntity<?> authenticate(UserLoginRequest input) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(input.getUsername(), input.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateToken(input.getUsername());
        String refreshToken = jwtUtils.generateRefreshToken(input.getUsername());

        return ResponseEntity.ok(new JwtResponse(jwt, refreshToken));
    }
}
