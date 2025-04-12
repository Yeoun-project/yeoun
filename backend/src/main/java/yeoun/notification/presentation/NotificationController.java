package yeoun.notification.presentation;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import yeoun.common.SuccessResponse;
import yeoun.notification.dto.response.NotificationResponse;
import yeoun.question.dto.response.QuestionResponse;
import yeoun.notification.domain.Notification;
import yeoun.question.domain.Question;
import yeoun.auth.service.JwtService;
import yeoun.notification.service.NotificationService;
import yeoun.notification.domain.NotificationType;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping("/api/notification")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping("/connect")
    public ResponseEntity<SseEmitter> connect() {
        Long userId = JwtService.getUserIdFromAuthentication();

        SseEmitter emitter = notificationService.getConnect(userId);

        return ResponseEntity.ok().body(emitter);
    }

    @GetMapping
    public ResponseEntity<?> getNotifications() {
        Long userId = JwtService.getUserIdFromAuthentication();

        List<Notification> entityList = notificationService.getAllNotifications(userId);

        List<NotificationResponse> dtoList = entityList.stream()
                .map(e-> NotificationResponse.of(e))
                .toList();

        Map<String, Object> response = Map.of("notifications", dtoList);

        return ResponseEntity.ok().body(new SuccessResponse("success get all notifications", response));
    }

    @GetMapping("/{notificationId}")
    public void readNotification(@PathVariable("notificationId") Long notificationId, HttpServletResponse response)
        throws IOException {
        Long userId = JwtService.getUserIdFromAuthentication();

        Question question = notificationService.getQuestionByNotification(userId, notificationId);

        response.sendRedirect("front question url / " + question.getId());
        return;
    }

    @PostMapping("/test")
    public ResponseEntity<?> testAddNotification() {
        notificationService.addNotification(JwtService.getUserIdFromAuthentication(), NotificationType.COMMENT, 1l);
        return ResponseEntity.ok().build();
    }
}
