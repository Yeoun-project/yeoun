package yeoun.auth.service;

import yeoun.auth.vo.KakaoToken;
import yeoun.auth.vo.KakaoUserInfo;
import yeoun.user.domain.User;
import yeoun.user.domain.repository.UserRepository;
import yeoun.user.service.UserService;
import yeoun.util.FormattingUtil;
import yeoun.user.vo.UserRegisterInfoVo;
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

    public User getUserFromKakao(String code) {

        KakaoToken kakaoToken = WebClient.create(KAUTH_TOKEN_URL_HOST).post()
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
                .bodyToMono(KakaoToken.class)
                .block();

        KakaoUserInfo kakaoUserInfo = getUserInfo(kakaoToken.getAccessToken());
        Optional<User> findUser = userRepository.findByOAuthId(kakaoUserInfo.getId());

        return findUser.orElseGet(() -> userService.registerByUserInfo(
                UserRegisterInfoVo.builder()
                        .oAuthId(kakaoUserInfo.getId())
                        .name(kakaoUserInfo.getKakaoAccount().getName())
                        .email(kakaoUserInfo.getKakaoAccount().getEmail())
                        .phone(FormattingUtil.formatPhoneNumber(kakaoUserInfo.getKakaoAccount().getPhone()))
                        .oAuthPlatform("KAKAO")
                        .build()
        ));
    }

    public KakaoUserInfo getUserInfo(String accessToken) {

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
                .bodyToMono(KakaoUserInfo.class)
                .block();
    }

}