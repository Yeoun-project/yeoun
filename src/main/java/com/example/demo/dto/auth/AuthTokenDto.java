package com.example.demo.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthTokenDto {

    private String accessToken;

    private String refreshToken;
}
