package yeoun.user.presentation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import yeoun.auth.service.JwtService;
import yeoun.user.dto.request.IsNotificationRequest;
import yeoun.user.service.UserService;
import yeoun.user.domain.User;
import yeoun.user.dto.response.UserAuthResponse;
import yeoun.user.dto.response.UserNotificationResponse;

@Controller
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @DeleteMapping()
    public ResponseEntity<?> deleteUser() {
        userService.deleteUser(JwtService.getUserIdFromAuthentication());
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @GetMapping("/oAuth")
    public ResponseEntity<?> getUserAuthPlatform() {
        User user = userService.getUserInfo(JwtService.getUserIdFromAuthentication());

        return ResponseEntity.status(HttpStatus.OK).body(UserAuthResponse.of(user));
    }

    @GetMapping("/notification")
    public ResponseEntity<?> getUserAlarm() {
        User user = userService.getUserInfo(JwtService.getUserIdFromAuthentication());

        return ResponseEntity.status(HttpStatus.OK).body(UserNotificationResponse.of(user));
    }

    @PostMapping("/notification")
    public ResponseEntity<?> setUserAlarm(@RequestBody @Valid IsNotificationRequest dto) {
        userService.setIsNotification(dto, JwtService.getUserIdFromAuthentication());
        return ResponseEntity.status(HttpStatus.OK).body("설정 성공");
    }
}
