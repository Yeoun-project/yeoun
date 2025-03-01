package com.example.demo.jwt;

import com.example.demo.service.CustomUserDetailsService;
import com.example.demo.util.CookieUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtRefreshTokenFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String refreshToken = CookieUtil.getTokenFromCookies("refreshToken", request);

        if (refreshToken == null) {
            chain.doFilter(request, response);
            return;
        }

        try {
            String userId = jwtUtil.extractUserId(refreshToken);

            if (userId != null && jwtUtil.validateToken(refreshToken)) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(userId);

                String newAccessToken = jwtUtil.generateAccessToken(userDetails.getUsername());
                String newRefreshToken = jwtUtil.generateRefreshToken(userDetails.getUsername());

                CookieUtil.addCookie(response, "accessToken", newAccessToken, 60 * 60);
                CookieUtil.addCookie(response, "refreshToken", newRefreshToken, 60 * 60 * 24 * 7);

                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);

                log.info("accessToken, refreshToken 갱신 완료, 사용자 PK: {}", userId);
            }
        } catch (Exception e) {
            log.error("Refresh Token 인증 실패, {}", e.getLocalizedMessage());
        }

        chain.doFilter(request, response);
    }

}
