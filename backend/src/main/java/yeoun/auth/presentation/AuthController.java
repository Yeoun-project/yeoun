package yeoun.auth.presentation;

import yeoun.common.ErrorResponse;
import yeoun.common.SuccessResponse;
import yeoun.user.domain.UserEntity;
import yeoun.auth.service.JwtService;
import yeoun.auth.service.GoogleService;
import yeoun.auth.service.KakaoService;
import yeoun.auth.service.NaverService;
import yeoun.auth.infrastructure.CookieUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/public/auth")
@RequiredArgsConstructor
public class AuthController {

    @Value("${jwt.access-token-expiration-time}")
    private long accessTokenExpirationTime;
    @Value("${jwt.refresh-token-expiration-time}")
    private long refreshTokenExpirationTime;

    private final KakaoService kakaoService;
    private final NaverService naverService;
    private final GoogleService googleService;
    private final JwtService jwtService;

    @GetMapping("/login/kakao")
    public ResponseEntity<?> kakaoLogin(@RequestParam("code") String code, HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            UserEntity user = kakaoService.getUserFromKakao(code);
            generateAndAddTokenCookie(user, request, response);
            response.sendRedirect("https://localhost:5173/today-question");

            return ResponseEntity.ok(new SuccessResponse("Login successful by Kakao", null));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse("", "Access denied"));
        }
    }

    @GetMapping("/login/naver")
    public ResponseEntity<?> naverLogin(@RequestParam("code") String code, HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            UserEntity user = naverService.getUserFromNaver(code);
            generateAndAddTokenCookie(user, request, response);
            response.sendRedirect("https://localhost:5173/today-question");

            return ResponseEntity.ok(new SuccessResponse("Login successful by Naver", null));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse("", "Access denied"));
        }
    }

    @GetMapping("/login/google")
    public ResponseEntity<?> googleLogin(@RequestParam("code") String code, HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            UserEntity user = googleService.getUserFromGoogle(code);
            generateAndAddTokenCookie(user, request, response);
            response.sendRedirect("https://localhost:5173/today-question");

            return ResponseEntity.ok(new SuccessResponse("Login successful by Google", null));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse("", "Access denied"));
        }
    }

    private void generateAndAddTokenCookie(UserEntity user, HttpServletRequest request, HttpServletResponse response) {
        String ip = JwtService.getIpFromRequest(request);

        String accessToken = jwtService.generateAccessToken(user, ip);
        String refreshToken = jwtService.generateRefreshToken(user);

        CookieUtil.addCookie(response, "accessToken", accessToken, accessTokenExpirationTime);
        CookieUtil.addCookie(response, "refreshToken", refreshToken, refreshTokenExpirationTime);

        removeAnonymousToken(response);
    }

    private void removeAnonymousToken(HttpServletResponse response) {
        if(JwtService.getAnonymousTokenAuthentication() != null)
            CookieUtil.addCookie(response, "anonymousToken", null, 0L);
    }

}

