package yeoun.user.presentation;

import jakarta.validation.Valid;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
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
import yeoun.auth.infrastructure.CookieUtil;
import yeoun.auth.service.JwtService;
import yeoun.user.dto.request.IsNotificationRequest;
import yeoun.common.SuccessResponse;
import yeoun.user.service.UserService;
import yeoun.user.domain.User;
import yeoun.user.dto.response.UserAuthResponse;
import yeoun.user.dto.response.UserNotificationResponse;

@Controller
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUser(@PathParam("hard") Boolean hard, HttpServletResponse response) {
        Long userId = JwtService.getUserIdFromAuthentication();
        if(hard) {
            userService.hardDeleteAll(userId);
        }else{
            userService.softDeleteUser(userId);
        }
        // logout
        CookieUtil.logout(response);

        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse("success", null));
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) {

        CookieUtil.logout(response);

        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse("success", null));
    }

    @GetMapping("/oAuth")
    public ResponseEntity<?> getUserAuthPlatform() {
        User user = userService.getUserInfo(JwtService.getUserIdFromAuthentication());

        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse("success get Auth",UserAuthResponse.of(user)));
    }

    @GetMapping("/notification")
    public ResponseEntity<?> getUserAlarm() {
        User user = userService.getUserInfo(JwtService.getUserIdFromAuthentication());

        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse("success",UserNotificationResponse.of(user)));
    }

    @PostMapping("/notification")
    public ResponseEntity<?> setUserAlarm(@RequestBody @Valid IsNotificationRequest dto) {
        userService.setIsNotification(dto, JwtService.getUserIdFromAuthentication());
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse("success"));
    }
}
