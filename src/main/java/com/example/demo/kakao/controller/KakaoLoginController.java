package com.example.demo.kakao.controller;

import com.example.demo.kakao.dto.KakaoUserInfoResponseDto;
import com.example.demo.kakao.service.KakaoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public")
public class KakaoLoginController {

    private final KakaoService kakaoService;

    @Autowired
    public KakaoLoginController(KakaoService kakaoService) {
        this.kakaoService = kakaoService;
    }

    @Value("${kakao.client_id}")
    private String clientId;

    @Value("${kakao.redirect_uri}")
    private String redirectUri;

    @GetMapping("/login/kakao")
    public String kakaoLogin() {
        return "https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=" + clientId + "&redirect_uri=" + redirectUri;
    }

    @GetMapping("/oauth/kakao")
    public KakaoUserInfoResponseDto callback(@RequestParam("code") String code) {
        String accessToken = kakaoService.getAccessTokenFromKakao(code);
        return kakaoService.getUserInfo(accessToken);
    }
}