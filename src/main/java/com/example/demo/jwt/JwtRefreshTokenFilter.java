package com.example.demo.jwt;

import com.example.demo.entity.UserEntity;
import com.example.demo.util.CookieUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtRefreshTokenFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final RefreshRepository refreshRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String refreshToken = CookieUtil.getTokenFromCookies("refreshToken", request);

        if (refreshToken == null) {
            chain.doFilter(request, response);
            return;
        }

        try {
            Map<String, String> token = jwtUtil.extractToken(refreshToken);

            String userId = token.get("subject");
            String token_uuid = token.get("uuid");

            // db에서 조회
            RefreshEntity dbRefresh = refreshRepository.findById(Long.valueOf(userId)).get();
            UserEntity dbUser = dbRefresh.getUser();

            // token 검증
            if(dbRefresh==null || !dbRefresh.getUuid().equals(token_uuid)){
                throw new RuntimeException("db refresh uuid is null or not correct with token uuid");
            }

            // security context에 authenticaion 저장
            SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(userId, null, dbUser.getAuthorities()));

            // 새로운 access, refresh token 발급
            String newAccess = jwtUtil.generateAccessToken(dbUser, JwtUtil.getIpFromRequest(request));
            String newRefresh = jwtUtil.generateRefreshToken(dbUser);

            CookieUtil.addCookie(response, "accessToken", newAccess, 60 * 60);
            CookieUtil.addCookie(response, "refreshToken", newRefresh, 60 * 60 * 24 * 7);

            log.info("refresh 성공, 사용자 PK: {}, 권한: {}", userId, dbUser.getAuthorities());

        } catch (Exception e) {
            log.error("refresh 실패!, {}", e.getLocalizedMessage());
        }
        chain.doFilter(request, response);
    }

}
