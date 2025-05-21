package yeoun.user.presentation;

import static yeoun.auth.infrastructure.CookieUtil.logout;
import static yeoun.auth.service.JwtService.getUserIdFromAuthentication;

import jakarta.validation.Valid;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import yeoun.user.dto.request.IsNotificationRequest;
import yeoun.common.SuccessResponse;
import yeoun.user.dto.request.UserWithdrawRequest;
import yeoun.user.service.UserService;
import yeoun.user.domain.User;
import yeoun.user.dto.response.UserAuthResponse;
import yeoun.user.dto.response.UserNotificationResponse;

@Controller
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/delete")
    public ResponseEntity<?> deleteUser(
            @RequestParam("isHard") Boolean isHard,
            @RequestBody @Valid UserWithdrawRequest userWithDrawRequest,
            HttpServletResponse response
    ) {
        Long userId = getUserIdFromAuthentication();
        userService.withdraw(userWithDrawRequest, isHard, userId);
        logout(response);
        return ResponseEntity.ok().body(new SuccessResponse("탈퇴를 성공했습니다.", null));
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logoutController(HttpServletResponse response) {

        logout(response);

        return ResponseEntity.ok().body(new SuccessResponse("success", null));
    }

    @GetMapping("/oAuth")
    public ResponseEntity<?> getUserAuthPlatform() {
        User user = userService.getUserInfo(getUserIdFromAuthentication());

        return ResponseEntity.ok().body(new SuccessResponse("success get Auth", UserAuthResponse.of(user)));
    }

    @GetMapping("/notification")
    public ResponseEntity<?> getUserAlarm() {
        User user = userService.getUserInfo(getUserIdFromAuthentication());

        return ResponseEntity.ok().body(new SuccessResponse("success", UserNotificationResponse.of(user)));
    }

    @PostMapping("/notification")
    public ResponseEntity<?> setUserAlarm(@RequestBody @Valid IsNotificationRequest dto) {
        userService.setIsNotification(dto, getUserIdFromAuthentication());
        return ResponseEntity.ok().body(new SuccessResponse("success"));
    }
}
