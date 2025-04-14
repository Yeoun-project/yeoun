package yeoun.notification.presentation;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import yeoun.common.SuccessResponse;
import yeoun.notification.dto.response.NotificationResponseDto;
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

    @GetMapping
    public ResponseEntity<?> getNotifications() {
        Long userId = JwtService.getUserIdFromAuthentication();

        List<NotificationResponseDto> dtoList = notificationService.getAllNotifications(userId);

        Map<String, Object> response = Map.of("notifications", dtoList);

        return ResponseEntity.ok().body(new SuccessResponse("success get all notifications", response));
    }

    @GetMapping("/{questionId}")
    public ResponseEntity<?> readNotification(@PathVariable("questionId") Long questionId)
        throws IOException {
        Long userId = JwtService.getUserIdFromAuthentication();

        Question question = notificationService.getQuestionFromNotification(userId, questionId);

        return questionController.getQuestionDetails(questionId);
    }

    @PostMapping("/test")
    public ResponseEntity<?> testAddNotification(@RequestBody Map<String, Object> map) {
        notificationService.addNotification(JwtService.getUserIdFromAuthentication(),Long.valueOf(map.get("receiver").toString()), NotificationType.valueOf(map.get("type").toString()), Long.valueOf(map.get("questionId").toString()));
        return ResponseEntity.ok().build();
    }
}
