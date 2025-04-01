package yeoun.auth.filter;

import yeoun.auth.service.JwtService;
import yeoun.user.domain.UserEntity;
import yeoun.user.service.UserService;
import yeoun.user.domain.Role;
import yeoun.auth.infrastructure.CookieUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAnonymousTokenFilter extends OncePerRequestFilter {

    @Value("${scheduler.user_history_delete_second}")
    private Long userHistoryDeleteSecond;

    private final JwtService jwtService;
    private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {

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
            Map<String, String> claims = jwtService.extractToken(anonymousToken, "role");
            authentication = new UsernamePasswordAuthenticationToken(Long.valueOf(claims.get("subject")), null,  Role.getRole(claims.get("role")).getAuthorities());
            CookieUtil.addCookie(response, "anonymousToken", anonymousToken,  userHistoryDeleteSecond);
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken generateNewAuthentication(HttpServletRequest request, HttpServletResponse response) {
        UserEntity newUser = userService.registerAnonymousUser();

        String token = jwtService.generateAnonymousToken(newUser);

        CookieUtil.addCookie(response, "anonymousToken", token,  userHistoryDeleteSecond);

        return new UsernamePasswordAuthenticationToken(newUser.getId(), null, newUser.getAuthorities());
    }

}
