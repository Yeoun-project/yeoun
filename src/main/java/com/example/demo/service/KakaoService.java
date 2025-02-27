package com.example.demo.service;

import com.example.demo.dto.KakaoTokenResponseDto;
import com.example.demo.dto.KakaoUserInfoResponseDto;
import com.example.demo.dto.auth.AuthTokenDto;
import com.example.demo.entity.UserEntity;
import com.example.demo.repository.UserRepository;
import io.netty.handler.codec.http.HttpHeaderValues;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoService {

    @Value("${kakao.client_id}")
    private String clientId;

    private String KAUTH_TOKEN_URL_HOST = "https://kauth.kakao.com";
    private String KAUTH_USER_URL_HOST = "https://kapi.kakao.com";

    private final UserRepository userRepository;

    public AuthTokenDto getTokenFromKakao(String code) {

        KakaoTokenResponseDto kakaoTokenResponseDto = WebClient.create(KAUTH_TOKEN_URL_HOST).post()
                .uri(uriBuilder -> uriBuilder
                        .scheme("https")
                        .path("/oauth/token")
                        .queryParam("grant_type", "authorization_code")
                        .queryParam("client_id", clientId)
                        .queryParam("code", code)
                        .build(true))
                .header(HttpHeaders.CONTENT_TYPE, HttpHeaderValues.APPLICATION_X_WWW_FORM_URLENCODED.toString())
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> Mono.error(new RuntimeException("Invalid Parameter")))
                .onStatus(HttpStatusCode::is5xxServerError, clientResponse -> Mono.error(new RuntimeException("Internal Server Error")))
                .bodyToMono(KakaoTokenResponseDto.class)
                .block();

        log.info("Access Token: {}", kakaoTokenResponseDto.getAccessToken());
        log.info("Refresh Token: {}", kakaoTokenResponseDto.getRefreshToken());

        KakaoUserInfoResponseDto dto = getUserInfo(kakaoTokenResponseDto.getAccessToken());
        Optional<UserEntity> findUser = userRepository.findByKakaoId(dto.id);

        if (findUser.isEmpty()) registerByUserInfo(dto);

        return new AuthTokenDto(kakaoTokenResponseDto.getAccessToken(), kakaoTokenResponseDto.getRefreshToken());
    }

    public void registerByUserInfo(KakaoUserInfoResponseDto dto) {

        UserEntity newUser = UserEntity.builder()
                .kakaoId(dto.getId())
                .name(dto.getKakaoAccount().getName())
                .email(dto.getKakaoAccount().getEmail())
                .role("ROLE_USER")
                .build();

        userRepository.save(newUser);
    }

    public KakaoUserInfoResponseDto getUserInfo(String accessToken) {

        KakaoUserInfoResponseDto userInfo = WebClient.create(KAUTH_USER_URL_HOST)
                .get()
                .uri(uriBuilder -> uriBuilder
                        .scheme("https")
                        .path("/v2/user/me")
                        .build(true))
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .header(HttpHeaders.CONTENT_TYPE, HttpHeaderValues.APPLICATION_X_WWW_FORM_URLENCODED.toString())
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> Mono.error(new RuntimeException("Invalid Parameter")))
                .onStatus(HttpStatusCode::is5xxServerError, clientResponse -> Mono.error(new RuntimeException("Internal Server Error")))
                .bodyToMono(KakaoUserInfoResponseDto.class)
                .block();

        return userInfo;
    }
}