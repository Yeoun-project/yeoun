package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class GoogleUserInfoResponseDto {
    private String id;
    private String email;
    private String name;
    private boolean verified_email;
    private String given_name;
    private String fmail_name;
    private String picture; // picture url
}
