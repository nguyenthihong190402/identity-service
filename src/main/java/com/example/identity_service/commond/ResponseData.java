package com.example.identity_service.commond;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.http.ResponseEntity;

public class ResponseData<T> {
    @JsonIgnore
    private HttpStatus status;
    @JsonProperty("status")
    private int statusCode;
    @JsonProperty("message")
    private String message;
    @JsonProperty("data")
    private T data;

    public ResponseData(HttpStatus status, String message, T data) {
        this.data = data;
        this.status = status;
        this.message = message;
        this.statusCode = status.value();
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
