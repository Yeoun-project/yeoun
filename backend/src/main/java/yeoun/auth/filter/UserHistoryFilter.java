package yeoun.auth.filter;

import yeoun.auth.service.JwtService;
import yeoun.exception.CustomException;
import yeoun.exception.ErrorCode;
import yeoun.user.service.UserHistoryService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserHistoryFilter extends OncePerRequestFilter {

    private final UserHistoryService userHistoryService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if (request.getRequestURI().contains("/public/auth/login")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 로그인을 제외한 모든 접근 client가 Anonymous로 인한 authentication을 가지고 있어야 함
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR, "UserHistoryFilter :: no authentication error"); // 로직상 실행되어서는 안되는 exception발생
        }

        Long userId = (Long) authentication.getPrincipal();

        if (userId == null) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR, "UserHistoryFilter :: user_id is null error");
        }

        userHistoryService.addHistory(userId, JwtService.getIpFromRequest(request), request.getHeader("User-Agent"), request.getRequestURI());

        filterChain.doFilter(request, response);
    }

}
