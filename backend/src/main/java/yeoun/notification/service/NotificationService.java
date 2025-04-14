package yeoun.notification.service;

import java.util.Optional;
import yeoun.notification.domain.NotificationType;
import yeoun.notification.domain.repository.NotificationDao;
import yeoun.notification.dto.response.NotificationResponseDto;
import yeoun.notification.domain.Notification;
import yeoun.question.domain.Question;
import yeoun.question.domain.repository.QuestionRepository;
import yeoun.user.domain.User;
import yeoun.exception.CustomException;
import yeoun.exception.ErrorCode;
import yeoun.notification.domain.repository.NotificationRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationService {

//    @Value("${sse.connection_time}")
    private final Long SSE_CONNECTION_TIME = Long.MAX_VALUE;
    private final Long SEE_RECONNECTION_TIME = 1000 * 10L; // 10초

    private final NotificationRepository notificationRepository;
    private final QuestionRepository questionRepository;
    private final EntityManager entityManager;

    private static HashMap<Long, SseEmitter> emitterMap = new HashMap<>();

    public SseEmitter getConnect(Long userId) {
        SseEmitter oldEmitter = emitterMap.get(userId);
        if(oldEmitter != null) {
            oldEmitter.complete();
        }

        SseEmitter emitter = new SseEmitter(SSE_CONNECTION_TIME);
        emitter.onTimeout(()->{ emitterMap.remove(userId); });
        emitter.onCompletion(() -> { emitterMap.remove(userId); });
        emitterMap.put(userId, emitter);

        sendUnReadNotificationCount(userId);

        return emitter;
    }

    @Transactional
    public List<NotificationResponseDto> getAllNotifications(Long userId) {
        List<NotificationDao> daos = notificationRepository.findAllNotifications(userId);

        List<NotificationResponseDto> dtos = daos.stream().map(NotificationResponseDto::of).toList();

        notificationRepository.setReadAll(userId);

        sendUnReadNotificationCount(userId);

        return dtos;
    }

    @Transactional
    public Question getQuestionFromNotification(Long userId, Long questionId) {
        Question question = notificationRepository.getQuestion(userId, questionId).orElseThrow(
            () -> new CustomException(ErrorCode.NOT_FOUND, "no notification with question id")
        );

        notificationRepository.removeByQuestion(userId, questionId);

        return question;
    }

    @Transactional
    public void addNotification(Long senderId, Long receiverId, NotificationType type, Long questionId) {
        Optional<Question> question = questionRepository.findById(questionId);

        if(question.isEmpty())
            throw new CustomException(ErrorCode.NOT_FOUND, "question not found");

        Notification noti = notificationRepository.save(
            Notification.builder()
                .notificationType(type)
                .question(question.get())
                .sender(entityManager.getReference(User.class, senderId))
                .receiver(entityManager.getReference(User.class, receiverId))
                .isRead(false)
                .build()
        );

        sendUnReadNotificationCount(receiverId);
    }

    // Sse 관련 함수들
    public void sendUnReadNotificationCount(Long userId) {
        Integer count = notificationRepository.getUnReadNotificationsCount(userId);

        sendSSEData(emitterMap.get(userId), count);
    }

    private void sendSSEData(SseEmitter sseEmitter, Object data) {
        log.info("try send " + data.toString());
        if(sseEmitter == null) {
            log.info("sseEmitter is null");
            return;
        }

        try {
            sseEmitter.send(SseEmitter.event().name("notification").data(data.toString()).reconnectTime(SEE_RECONNECTION_TIME).build());
            log.info("sseEmitter sended");
        } catch (IOException e) {
            log.info("sseEmitter send error");
        }
        log.info("Sse send end");
    }
}
