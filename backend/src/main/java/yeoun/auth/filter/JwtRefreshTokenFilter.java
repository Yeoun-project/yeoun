package yeoun.auth.filter;

import yeoun.auth.service.JwtService;
import yeoun.user.domain.UserEntity;
import yeoun.user.domain.repository.UserRepository;
import yeoun.auth.infrastructure.CookieUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Map;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtRefreshTokenFilter extends OncePerRequestFilter {

    @Value("${jwt.access-token-expiration-time}")
    private long accessTokenExpirationTime;
    @Value("${jwt.refresh-token-expiration-time}")
    private long refreshTokenExpirationTime;

    private final JwtService jwtService;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String refreshToken = CookieUtil.getTokenFromCookies("refreshToken", request);

        if (refreshToken == null || SecurityContextHolder.getContext().getAuthentication() != null) {
            chain.doFilter(request, response);
            return;
        }

        try {
            Map<String, String> token = jwtService.extractToken(refreshToken, "uuid");

            String userId = token.get("subject");
            String token_uuid = token.get("uuid");

            // db에서 조회
            UserEntity dbUser = userRepository.findById(Long.valueOf(userId)).get();

            // token 검증
            if (!dbUser.getUuid().equals(token_uuid)) {
                throw new RuntimeException("db uuid is null or not correct with token uuid (userId:" + userId + ")");
            }

            // security context에 authenticaion 저장
            SecurityContextHolder.getContext().setAuthentication(
                    new UsernamePasswordAuthenticationToken(dbUser.getId(), "refreshToken", dbUser.getAuthorities()));

            // 새로운 access, refresh token 발급
            String newAccess = jwtService.generateAccessToken(dbUser, JwtService.getIpFromRequest(request));
            String newRefresh = jwtService.generateRefreshToken(dbUser);

            CookieUtil.addCookie(response, "accessToken", newAccess, accessTokenExpirationTime);
            CookieUtil.addCookie(response, "refreshToken", newRefresh, refreshTokenExpirationTime);

            log.info("refresh 성공, 사용자 PK: {}, 권한: {}", userId, dbUser.getAuthorities());

        } catch (Exception e) {
            log.error("refresh 실패!, {}", e.getLocalizedMessage());
        }
        chain.doFilter(request, response);
    }

}
