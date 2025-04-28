package yeoun.user.presentation;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import yeoun.auth.infrastructure.CookieUtil;
import yeoun.auth.service.JwtService;
import yeoun.common.SuccessResponse;
import yeoun.user.service.UserService;

@Controller
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUser() {
        userService.deleteUser(JwtService.getUserIdFromAuthentication());
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse(null));
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) {

        // check if anonymous
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        CookieUtil.addCookie(response, "accessToken", "", 0L);
        CookieUtil.addCookie(response, "refreshToken", "", 0L);

        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse(null));
    }
}
