package com.example.demo.jwt;

import com.example.demo.type.Role;
import com.example.demo.util.CookieUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAccessTokenFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        String accessToken = CookieUtil.getTokenFromCookies("accessToken", request);

        // 모든 uri에 대하여 실행함 (request uri 확인 절차 없음)

        if (accessToken == null || accessToken.isEmpty()) {
            chain.doFilter(request, response);
            return;
        }

        try {
            Map<String, String> token = jwtUtil.extractToken(accessToken, "ip", "role");

            String userId = token.get("subject");
            Collection<? extends GrantedAuthority> authorities = Role.getRole(token.get("role")).getAuthorities();
            String ip = token.get("ip");

            // token 검증
            if(ip.isEmpty() || !ip.equals(JwtUtil.getIpFromRequest(request))){
                throw new RuntimeException("strange access token");
            }

            // security context에 authenticaion 저장
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(userId, null, authorities));

            log.info("인증 성공, 사용자 PK: {}, 권한: {}", userId, authorities);

        } catch (Exception e) {
            e.printStackTrace();
            //log.error("인증 실패!, {}", e.getMessage());
        }
        chain.doFilter(request, response);
    }
}
