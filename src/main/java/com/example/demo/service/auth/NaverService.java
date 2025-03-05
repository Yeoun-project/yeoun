package com.example.demo.service.auth;

import com.example.demo.dto.response.NaverTokenResponseDto;
import com.example.demo.dto.response.NaverUserInfoResponseDto;
import com.example.demo.entity.UserEntity;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import com.example.demo.vo.UserRegisterInfoVo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NaverService {

    @Value("${naver.client_id}")
    private String clientId;

    @Value("${naver.client_secret}")
    private String clientSecret;

    private String NAUTH_TOKEN_URL_HOST = "https://nid.naver.com";
    private String NAUTH_USER_URL_HOST = "https://openapi.naver.com";

    private final UserService userService;
    private final UserRepository userRepository;

    public UserEntity getUserFromNaver(String code) {

        NaverTokenResponseDto naverTokenResponseDto = WebClient.create(NAUTH_TOKEN_URL_HOST).post()
                .uri(uriBuilder -> uriBuilder
                        .scheme("https")
                        .path("/oauth2.0/token")
                        .queryParam("grant_type", "authorization_code")
                        .queryParam("client_id", clientId)
                        .queryParam("client_secret", clientSecret)
                        .queryParam("code", code)
                        .queryParam("state", UUID.randomUUID().toString())
                        .build(true))
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> Mono.error(new RuntimeException("Invalid Parameter")))
                .onStatus(HttpStatusCode::is5xxServerError, clientResponse -> Mono.error(new RuntimeException("Internal Server Error")))
                .bodyToMono(NaverTokenResponseDto.class)
                .block();

        NaverUserInfoResponseDto naverUserInfoResponseDto = getUserInfo(naverTokenResponseDto.getAccessToken());
        Optional<UserEntity> findUser = userRepository.findByOAuthId(naverUserInfoResponseDto.getId());

        System.out.println(findUser);
        return findUser.orElseGet(() -> userService.registerByUserInfo(
                UserRegisterInfoVo.builder()
                        .oAuthId(naverUserInfoResponseDto.getId())
                        .name(naverUserInfoResponseDto.getName())
                        .email(naverUserInfoResponseDto.getEmail())
                        .phone(naverUserInfoResponseDto.getPhone())
                        .oAuthPlatform("NAVER")
                        .build()
        ));
    }

    public NaverUserInfoResponseDto getUserInfo(String accessToken) {

        return WebClient.create(NAUTH_USER_URL_HOST).post()
                .uri(uriBuilder -> uriBuilder
                        .scheme("https")
                        .path("/v1/nid/me")
                        .build(true))
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> Mono.error(new RuntimeException("Invalid Parameter")))
                .onStatus(HttpStatusCode::is5xxServerError, clientResponse -> Mono.error(new RuntimeException("Internal Server Error")))
                .bodyToMono(Map.class)
                .map(response -> {
                    Map<String, Object> responseBody = (Map<String, Object>) response.get("response");
                    NaverUserInfoResponseDto userInfo = new NaverUserInfoResponseDto();
                    userInfo.setId((String) responseBody.get("id"));
                    userInfo.setName((String) responseBody.get("name"));
                    userInfo.setEmail((String) responseBody.get("email"));
                    userInfo.setPhone((String) responseBody.get("mobile"));
                    return userInfo;
                })
                .block();
    }
}
