package yeoun.user.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import yeoun.auth.service.JwtService;
import yeoun.user.service.UserService;

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
}
