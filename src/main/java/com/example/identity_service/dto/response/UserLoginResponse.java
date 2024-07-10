package com.example.identity_service.dto.response;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@RequiredArgsConstructor
public class UserLoginResponse {
    private String token;

    private Long expiresIn;
}
