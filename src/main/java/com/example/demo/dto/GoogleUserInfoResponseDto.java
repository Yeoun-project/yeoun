package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class GoogleUserInfoResponseDto {

    @JsonProperty("id")
    private String id;

    @JsonProperty("email")
    private String email;

    @JsonProperty("name")
    private String name;

    @JsonProperty("verified_email")
    private boolean verifiedEmail;

    @JsonProperty("given_name")
    private String givenName;

    @JsonProperty("fmail_name")
    private String fmailName;

    @JsonProperty("picture")
    private String picture;
}
