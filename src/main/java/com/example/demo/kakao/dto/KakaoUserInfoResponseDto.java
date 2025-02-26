package com.example.demo.kakao.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.HashMap;

@Getter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class KakaoUserInfoResponseDto {

    @JsonProperty("id")
    public Long id;

    @JsonProperty("has_signed_up")
    public Boolean hasSignedUp;

    @JsonProperty("connected_at")
    public Date connectedAt;

    @JsonProperty("synched_at")
    public Date synchedAt;

    @JsonProperty("properties")
    public HashMap<String, String> properties;

    @JsonProperty("kakao_account")
    public KakaoAccount kakaoAccount;

    @Getter
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public class KakaoAccount {

        //사용자 프로필 정보
        @JsonProperty("profile")
        public Profile profile;

        //카카오계정 이름
        @JsonProperty("name")
        public String name;

        // true : 유효한 이메일, false : 이메일이 다른 카카오 계정에 사용돼 만료
        @JsonProperty("is_email_valid")
        public Boolean isEmailValid;

        //true : 인증된 이메일, false : 인증되지 않은 이메일
        @JsonProperty("is_email_verified")
        public Boolean isEmailVerified;

        //카카오계정 대표 이메일
        @JsonProperty("email")
        public String email;

        @JsonProperty("age_range")
        public String ageRange;

        @JsonProperty("birthyear")
        public String birthYear;

        @JsonProperty("birthday")
        public String birthDay;

        @JsonProperty("birthday_type")
        public String birthDayType;

        //성별
        @JsonProperty("gender")
        public String gender;

        //국내 번호인 경우 +82 00-0000-0000 형식
        @JsonProperty("phone_number")
        public String phoneNumber;

        @Getter
        @NoArgsConstructor
        @JsonIgnoreProperties(ignoreUnknown = true)
        public class Profile {

            //닉네임
            @JsonProperty("nickname")
            public String nickName;

            //프로필 미리보기 이미지 URL
            @JsonProperty("thumbnail_image_url")
            public String thumbnailImageUrl;

            //프로필 사진 URL
            @JsonProperty("profile_image_url")
            public String profileImageUrl;

            //true : 기본 프로필, false : 사용자 등록
            @JsonProperty("is_default_image")
            public String isDefaultImage;

            //true : 기본 닉네임, false : 사용자 등록
            @JsonProperty("is_default_nickname")
            public Boolean isDefaultNickName;

        }
    }
}