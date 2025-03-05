package com.example.demo.service.auth;

import com.example.demo.entity.UserEntity;
import com.example.demo.dto.response.GoogleUserInfoResponseDto;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import com.example.demo.util.FormattingUtil;
import com.example.demo.vo.UserRegisterInfoVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class GoogleService {
    @Value("${google.client_id}")
    private String clientId;

    @Value("${google.client_secret}")
    private String clientSecret;

    @Value("${google.redirect_uri}")
    private String redirectUri;

    private String KAUTH_TOKEN_URL_HOST = "https://oauth2.googleapis.com";
    private String KAUTH_USER_URL_HOST = "https://www.googleapis.com";
    private String KUATH_PHONE_URL_HOST = "https://people.googleapis.com";

    private final UserRepository userRepository;
    private final UserService userService;

    public UserEntity getUserFromGoogle(String code) {
        String accessToken = getAccessTokenFromGoogle(code);

        GoogleUserInfoResponseDto info = getUserInfoFromGoogle(accessToken);

        Optional<UserEntity> findUser = userRepository.findByOAuthId(info.getId());

        return findUser.orElseGet(() -> userService.registerByUserInfo(UserRegisterInfoVo.builder()
                .oAuthId(info.getId())
                .name(info.getName())
                .email(info.getEmail())
                .phone(FormattingUtil.formatPhoneNumber(getPhoneNumberFromGoogle(accessToken, info.getId())))
                .oAuthPlatform("GOOGLE")
                .build()));
    }

    private String getAccessTokenFromGoogle(String code) {
        Map<String, Object> googleTokenResponse = WebClient.create(KAUTH_TOKEN_URL_HOST).post()
                .uri(uriBuilder -> uriBuilder
                        .scheme("https")
                        .path("/token")
                        .queryParam("client_id", clientId)
                        .queryParam("client_secret", clientSecret)
                        .queryParam("code", code)
                        .queryParam("redirect_uri", redirectUri)
                        .queryParam("grant_type", "authorization_code")
                        .build())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response -> response.bodyToMono(String.class)
                        .flatMap(body -> Mono.error(new RuntimeException("Error response: " + response.statusCode() + ", Body: " + body))))
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {
                })
                .block();

        return (String) googleTokenResponse.get("access_token");
    }

    private GoogleUserInfoResponseDto getUserInfoFromGoogle(String accessToken) {
        return WebClient.create(KAUTH_USER_URL_HOST).get()
                .uri(uriBuilder -> uriBuilder
                        .scheme("https")
                        .path("/oauth2/v2/userinfo")
                        .build())
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (response) -> Mono.error(new RuntimeException(response.toString())))
                .bodyToMono(GoogleUserInfoResponseDto.class)
                .block();
    }

    private String getPhoneNumberFromGoogle(String token, String userId) {
        return WebClient.create(KUATH_PHONE_URL_HOST).get()
                .uri("https://people.googleapis.com/v1/people/" + userId + "?personFields=phoneNumbers")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (response) -> response.bodyToMono(String.class)
                        .flatMap(body -> Mono.error(new RuntimeException("Error response: " + response.statusCode() + ", Body: " + body))))
                .bodyToMono(String.class)
                .block();
    }
}
