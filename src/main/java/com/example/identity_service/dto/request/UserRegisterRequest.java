package com.example.identity_service.dto.request;

import jakarta.persistence.Column;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class UserRegisterRequest {
    private String fullName;

    private String username;

    private String password;

}
