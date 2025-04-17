package yeoun.notification.presentation;

import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import yeoun.common.SuccessResponse;
import yeoun.notification.dto.response.NotificationListResponse;
import yeoun.auth.service.JwtService;
import yeoun.notification.service.NotificationService;
import yeoun.notification.domain.NotificationType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import yeoun.question.presentation.QuestionController;

@RestController
@RequestMapping("/api/notification")
@RequiredArgsConstructor
@Slf4j
public class NotificationController {

    private final NotificationService notificationService;
    private final QuestionController questionController;

    @GetMapping("/connect")
    public ResponseEntity<SseEmitter> connect() {
        Long userId = JwtService.getUserIdFromAuthentication();
        SseEmitter emitter = notificationService.getConnect(userId);
        return ResponseEntity.ok().body(emitter);
    }

    @GetMapping()
    public ResponseEntity<?> getNotifications() {
        Long userId = JwtService.getUserIdFromAuthentication();
        NotificationListResponse notificationListResponse = notificationService.getAllNotifications(userId);
        return ResponseEntity.ok().body(
                new SuccessResponse("success get all notifications", notificationListResponse));
    }

    @GetMapping("/{questionId}")
    public ResponseEntity<?> readNotification(@PathVariable("questionId") Long questionId) {
        Long userId = JwtService.getUserIdFromAuthentication();
        notificationService.getQuestionFromNotification(userId, questionId);
        return questionController.getQuestionDetails(questionId);
    }

    @PostMapping("/test")
    public ResponseEntity<?> testAddNotification(@RequestBody Map<String, Object> map) {
        Long userId = JwtService.getUserIdFromAuthentication();
        notificationService.addNotification(
                userId,
                Long.valueOf(map.get("receiver").toString()),
                NotificationType.valueOf(map.get("type").toString()),
                Long.valueOf(map.get("questionId").toString())
        );
        return ResponseEntity.ok().build();
    }
}
