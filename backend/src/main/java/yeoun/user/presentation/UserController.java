package yeoun.user.presentation;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import yeoun.auth.infrastructure.CookieUtil;
import yeoun.auth.service.JwtService;
import yeoun.user.service.UserService;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/public/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) {

        // check if anonymous
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        if(!auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_USER"))) {
//            userService.eraseAnonymous((Long) auth.getPrincipal());
//        }

        CookieUtil.addCookie(response, "accessToken", "", 0L);
        CookieUtil.addCookie(response, "refreshToken", "", 0L);

        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}
