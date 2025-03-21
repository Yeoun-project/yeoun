package com.example.demo.jwt;

import com.example.demo.entity.UserEntity;
import com.example.demo.service.UserService;
import com.example.demo.type.Role;
import com.example.demo.util.CookieUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAnonymousTokenFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {

        // check url
        if (request.getRequestURI().equals("/public/auth/login")) {
            filterChain.doFilter(request, response);
            return ;
        }

        // check authentic from security context
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            filterChain.doFilter(request, response);
            return ;
        }

        // check anonymous token
        String anonymousToken = CookieUtil.getTokenFromCookies("anonymousToken", request);
        Authentication authentication;

        if(anonymousToken == null || anonymousToken.isEmpty()) {
            // generate new authentic and publish new Token
            authentication = generateNewAuthentication(request, response);
        }else {
            // get authentic from token
            Map<String, String> claims = jwtUtil.extractToken(anonymousToken, "role");
            Role.getRole(claims.get("role")).getAuthorities().forEach(a ->log.info(a.getAuthority()));
            authentication = new UsernamePasswordAuthenticationToken(Long.valueOf(claims.get("subject")), null,  Role.getRole(claims.get("role")).getAuthorities());
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken generateNewAuthentication(HttpServletRequest request, HttpServletResponse response) {
        UserEntity newUser = userService.registerAnonymousUser();

        String token = jwtUtil.generateAnonymousToken(newUser);

        CookieUtil.addCookie(response, "anonymousToken", token,  3372036854775807L);

        return new UsernamePasswordAuthenticationToken(newUser.getId(), null, newUser.getAuthorities());
    }

}
