package yeoun.notification.service;

import java.util.Optional;
import yeoun.notification.domain.NotificationType;
import yeoun.notification.dto.response.NotificationResponse;
import yeoun.notification.domain.NotificationEntity;
import yeoun.question.domain.QuestionEntity;
import yeoun.question.domain.repository.QuestionRepository;
import yeoun.user.domain.UserEntity;
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

        sendAllNotifications(emitter, userId);

        return emitter;
    }

    @Transactional
    public List<NotificationEntity> getAllNotifications(Long userId) {
        List<NotificationEntity> notifications = notificationRepository.getUnReadNotifications(userId);
        readAll(notifications);
        return notifications;
    }

    private void readAll(List<NotificationEntity> notifications) {
        List<Long> notificationIds = notifications.stream().map(NotificationEntity::getId).toList();
        notificationRepository.setReadAll(notificationIds);
    }

    @Transactional
    public QuestionEntity getQuestionByNotification(Long userId, Long notificationId) {
        Optional<NotificationEntity> notificationOptional = notificationRepository.getNotificationQuestionById(notificationId);

        if(notificationOptional.isEmpty()) {
            throw new CustomException(ErrorCode.NOT_FOUND, "notification not found");
        }

        NotificationEntity notification = notificationOptional.get();

        if(notification.getReceiver().getId() != userId) {
            throw new CustomException(ErrorCode.BAD_REQUEST, "not notification receiver");
        }

        removeNotification(notificationId);

        return notification.getQuestion();
    }

    private void removeNotification(Long notificationId) {
        notificationRepository.removeById(notificationId);
    }

    @Transactional
    public void addNotification(Long userId, NotificationType type, Long questionId) {
        Optional<QuestionEntity> question = questionRepository.findById(questionId);

        if(question.isEmpty())
            throw new CustomException(ErrorCode.NOT_FOUND, "question not found");

        notificationRepository.save(
            NotificationEntity.builder()
                .notificationType(type)
                .question(question.get())
                .receiver(entityManager.getReference(UserEntity.class, userId))
                .isRead(false)
                .build()
        );

        sendSSEData(emitterMap.get(userId), type.getContent(question.get().getContent()));
    }

    // Sse 관련 함수들
    public void sendAllNotifications(SseEmitter emitter, Long userId) {
        List<NotificationEntity> entityList = notificationRepository.getAllNotifications(userId);
        List<NotificationResponse> data = entityList.stream()
            .map(entity -> NotificationResponse.of(entity))
            .toList();

        sendSSEData(emitter, data);
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
