package yeoun.user.presentation;

import static yeoun.auth.infrastructure.CookieUtil.addCookie;
import static yeoun.auth.service.JwtService.getUserIdFromAuthentication;

import yeoun.common.SuccessResponse;
import yeoun.user.dto.request.IsNotificationRequest;
import yeoun.user.service.UserService;
import yeoun.user.domain.User;
import yeoun.user.dto.response.UserAuthResponse;
import yeoun.user.dto.response.UserNotificationResponse;
import jakarta.validation.Valid;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUser() {
        userService.deleteUser(getUserIdFromAuthentication());
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse(null));
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) {

        // check if anonymous
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        addCookie(response, "accessToken", "", 0L);
        addCookie(response, "refreshToken", "", 0L);

        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse(null));
    }

    @GetMapping("/oAuth")
    public ResponseEntity<?> getUserAuthPlatform() {
        User user = userService.getUserInfo(getUserIdFromAuthentication());

        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse("success get Auth",UserAuthResponse.of(user)));
    }

    @GetMapping("/notification")
    public ResponseEntity<?> getUserAlarm() {
        User user = userService.getUserInfo(getUserIdFromAuthentication());

        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse("success",UserNotificationResponse.of(user)));
    }

    @PostMapping("/notification")
    public ResponseEntity<?> setUserAlarm(@RequestBody @Valid IsNotificationRequest dto) {
        userService.setIsNotification(dto, getUserIdFromAuthentication());
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse("success"));
    }
}
