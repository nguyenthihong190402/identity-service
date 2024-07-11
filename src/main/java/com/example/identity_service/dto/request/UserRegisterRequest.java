package com.example.identity_service.dto.request;

import com.example.identity_service.dto.BaseDTO;
import com.example.identity_service.entity.BaseEntity;
import jakarta.persistence.Column;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

public class UserRegisterRequest extends BaseEntity {
    private String fullName;
    private String username;
    private String password;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
