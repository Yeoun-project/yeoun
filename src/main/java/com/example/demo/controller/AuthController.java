package com.example.demo.controller;

import com.example.demo.dto.auth.AuthTokenDto;
import com.example.demo.service.KakaoService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public/auth")
@RequiredArgsConstructor
public class AuthController {

    private final KakaoService kakaoService;

    @GetMapping("/login/kakao")
    public ResponseEntity<?> kakaoLogin(@RequestParam("code") String code, HttpServletResponse response) {
        try {
            AuthTokenDto tokenDto = kakaoService.getTokenFromKakao(code);

            String accessCookie = String.format("accessToken=%s; HttpOnly; Path=/; Max-Age=%d; SameSite=None",
                    tokenDto.getAccessToken(), 60 * 60);
            String refreshCookie = String.format("refreshToken=%s; HttpOnly; Path=/; Max-Age=%d; SameSite=None",
                    tokenDto.getRefreshToken(), 60 * 60 * 24 * 7);

            response.addHeader("Set-Cookie", accessCookie);
            response.addHeader("Set-Cookie", refreshCookie);

            return ResponseEntity.ok("Login successful");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Denied access");
        }
    }
}

