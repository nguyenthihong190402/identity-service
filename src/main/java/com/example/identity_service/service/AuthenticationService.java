package com.example.identity_service.service;

import com.example.identity_service.dto.request.UserLoginRequest;
import com.example.identity_service.dto.request.UserRegisterRequest;
import com.example.identity_service.entity.UserEntity;
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

import javax.xml.stream.events.EntityReference;

@Service
public class AuthenticationService {
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private final AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtils jwtUtils;
    public AuthenticationService(
            UserRepository userRepository,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseEntity<?> signup(UserRegisterRequest input) {
        UserEntity user = new UserEntity();
                user.setFullName(input.getFullName());
                user.setUsername(input.getUsername());
                user.setPassword(passwordEncoder.encode(input.getPassword()));
        return ResponseEntity.ok(userRepository.save(user));
    }

    public ResponseEntity<?> authenticate(UserLoginRequest input) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(input.getUsername(),input.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt= jwtUtils.generateJwtToken(authentication);

        return ResponseEntity.ok(jwt);
    }
}
