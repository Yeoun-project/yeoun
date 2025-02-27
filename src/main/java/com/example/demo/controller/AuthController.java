package com.example.demo.controller;

import com.example.demo.common.ErrorResponse;
import com.example.demo.common.SuccessResponse;
import com.example.demo.jwt.JwtUtil;
import com.example.demo.service.KakaoService;
import com.example.demo.util.CookieUtil;
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
    private final JwtUtil jwtUtil;

    @GetMapping("/login/kakao")
    public ResponseEntity<?> kakaoLogin(@RequestParam("code") String code, HttpServletResponse response) {
        try {
            Long userId = kakaoService.getUserIdFromKakao(code);

            String accessToken = jwtUtil.generateAccessToken(userId.toString());
            String refreshToken = jwtUtil.generateRefreshToken(userId.toString());

            CookieUtil.addCookie(response, "accessToken", accessToken, 60 * 60);
            CookieUtil.addCookie(response, "refreshToken", refreshToken, 60 * 60 * 24 * 7);

            return ResponseEntity.ok(new SuccessResponse("Login successful", ""));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse("", "Access denied"));
        }
    }
}

