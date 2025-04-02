package yeoun.notification.presentation;

import yeoun.notification.dto.response.NotificationResponse;
import yeoun.question.dto.response.QuestionResponse;
import yeoun.notification.domain.NotificationEntity;
import yeoun.question.domain.QuestionEntity;
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

        List<NotificationEntity> entityList = notificationService.getAllNotifications(userId);

        List<NotificationResponse> dtoList = entityList.stream()
                .map(e->new NotificationResponse(e.getId(), e.getContent(), e.getNotificationType()))
                .toList();

        return ResponseEntity.ok().body(dtoList);
    }

    @GetMapping("/{notificationId}")
    public ResponseEntity<?> readNotification(@PathVariable Long notificationId) {
        Long userId = JwtService.getUserIdFromAuthentication();

        QuestionEntity question = notificationService.readNotification(userId, notificationId);

        QuestionResponse dto = QuestionResponse.builder()
                .id(question.getId())
                .content(question.getContent())
                .heart(question.getHeart())
                .categoryName(question.getCategory().getName())
                .commentCount(question.getComments().size())
                .createTime(question.getCreatedDateTime())
                .build();

        return ResponseEntity.ok().body(dto);
    }

    @PostMapping("/test")
    public ResponseEntity<?> testAddNotification() {
        notificationService.addNotification(JwtService.getUserIdFromAuthentication(), new NotificationResponse("content", NotificationType.COMMENT));
        return ResponseEntity.ok().build();
    }
}
