package com.example.demo.common;

import lombok.Getter;

@Getter
public class ErrorResponse {

    private final String code;

    private String message;

    public ErrorResponse(String code) {
        this.code = code;
    }

    public ErrorResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }

}
