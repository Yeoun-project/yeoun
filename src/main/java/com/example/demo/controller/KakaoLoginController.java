package com.example.demo.controller;

import com.example.demo.dto.KakaoUserInfoResponseDto;
import com.example.demo.service.KakaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/public")
public class KakaoLoginController {

    private final KakaoService kakaoService;

    @Value("${kakao.client_id}")
    private String clientId;

    @Value("${kakao.redirect_uri}")
    private String redirectUri;

    @GetMapping("/auth/login/kakao")
    public String kakaoLogin() {
        return "https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=" + clientId + "&redirect_uri=" + redirectUri;
    }

    @GetMapping("/oauth/login/kakao")
    public KakaoUserInfoResponseDto callback(@RequestParam("code") String code) {
        String accessToken = kakaoService.getAccessTokenFromKakao(code);
        return kakaoService.getUserInfo(accessToken);
    }
}