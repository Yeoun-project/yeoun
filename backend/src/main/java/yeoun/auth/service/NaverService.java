package yeoun.auth.service;

import yeoun.auth.vo.NaverToken;
import yeoun.auth.vo.NaverUserInfo;
import yeoun.user.domain.UserEntity;
import yeoun.user.domain.repository.UserRepository;
import yeoun.user.service.UserService;
import yeoun.user.vo.UserRegisterInfoVo;
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

        NaverToken naverToken = WebClient.create(NAUTH_TOKEN_URL_HOST).post()
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
                .bodyToMono(NaverToken.class)
                .block();

        NaverUserInfo naverUserInfo = getUserInfo(naverToken.getAccessToken());
        Optional<UserEntity> findUser = userRepository.findByOAuthId(naverUserInfo.getId());

        System.out.println(findUser);
        return findUser.orElseGet(() -> userService.registerByUserInfo(
                UserRegisterInfoVo.builder()
                        .oAuthId(naverUserInfo.getId())
                        .name(naverUserInfo.getName())
                        .email(naverUserInfo.getEmail())
                        .phone(naverUserInfo.getPhone())
                        .oAuthPlatform("NAVER")
                        .build()
        ));
    }

    public NaverUserInfo getUserInfo(String accessToken) {

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
                    NaverUserInfo userInfo = new NaverUserInfo();
                    userInfo.setId((String) responseBody.get("id"));
                    userInfo.setName((String) responseBody.get("name"));
                    userInfo.setEmail((String) responseBody.get("email"));
                    userInfo.setPhone((String) responseBody.get("mobile"));
                    return userInfo;
                })
                .block();
    }
}
