package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class NaverUserInfoResponseDto {

    @JsonProperty("id")
    public String id;

    @JsonProperty("email")
    public String email;

    @JsonProperty("mobile")
    public String phone;

    @JsonProperty("name")
    public String name;


}
