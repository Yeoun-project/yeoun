package yeoun.notification.presentation;

import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import yeoun.common.SuccessResponse;
import yeoun.notification.domain.Notification;
import yeoun.notification.dto.response.NotificationDetailResponse;
import yeoun.notification.dto.response.NotificationListResponse;
import yeoun.question.domain.Question;
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
import yeoun.question.dto.response.QuestionDetailResponse;
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
    public ResponseEntity<?> getNotifications(@PageableDefault() final Pageable pageable) {
        Long userId = JwtService.getUserIdFromAuthentication();

        Slice<Notification> entityList = notificationService.getAllNotifications(userId,pageable);

        NotificationListResponse dtoList = NotificationListResponse
            .builder()
            .details(entityList.toList())
            .hasNext(entityList.hasNext())
            .build();

        return ResponseEntity.ok().body(new SuccessResponse("알람 가져오기 성공", dtoList));
    }

    @GetMapping("/{questionId}")
    public ResponseEntity<?> readNotification(@PathVariable("questionId") Long questionId) {
        Long userId = JwtService.getUserIdFromAuthentication();

        QuestionDetailResponse dto = notificationService.getQuestionFromNotification(userId, questionId);

        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse("질문 상세 조회를 성공했습니다.", dto));
    }

    @PostMapping("/test")
    public ResponseEntity<?> testAddNotification(@RequestBody Map<String, Object> map) {
        notificationService.addNotification(JwtService.getUserIdFromAuthentication(),Long.valueOf(map.get("receiver").toString()), NotificationType.valueOf(map.get("type").toString()), Long.valueOf(map.get("questionId").toString()));
        return ResponseEntity.ok().build();
    }
}