package com.example.demo.service;

import com.example.demo.dto.KakaoTokenResponseDto;
import com.example.demo.dto.KakaoUserInfoResponseDto;
import com.example.demo.entity.UserEntity;
import com.example.demo.repository.UserRepository;
import com.example.demo.util.FormattingUtil;
import com.example.demo.vo.UserRegisterInfoVo;
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

    private final UserService userService;
    private final UserRepository userRepository;

    public Long getUserIdFromKakao(String code) {

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

        KakaoUserInfoResponseDto kakaoUserInfoResponseDto = getUserInfo(kakaoTokenResponseDto.getAccessToken());
        Optional<UserEntity> findUser = userRepository.findByPhone(kakaoUserInfoResponseDto.getKakaoAccount().getPhone());

        UserEntity user = findUser.orElseGet(() -> userService.registerByUserInfo(
                UserRegisterInfoVo.builder()
                        .oAuthId(kakaoUserInfoResponseDto.getId())
                        .name(kakaoUserInfoResponseDto.getKakaoAccount().getName())
                        .email(kakaoUserInfoResponseDto.getKakaoAccount().getEmail())
                        .phone(FormattingUtil.formatPhoneNumber(kakaoUserInfoResponseDto.getKakaoAccount().getPhone()))
                        .oAuthPlatform("KAKAO")
                        .build()
        ));

        return user.getId();
    }

    public KakaoUserInfoResponseDto getUserInfo(String accessToken) {

        return WebClient.create(KAUTH_USER_URL_HOST)
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
    }

}