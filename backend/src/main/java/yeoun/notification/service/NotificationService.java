package yeoun.notification.service;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
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

        sendUnReadNotificationCount(emitter, userId);

        return emitter;
    }

    @Transactional
    public List<NotificationResponseDto> getAllNotifications(Long userId) {
        List<NotificationDao> daos = notificationRepository.findAllNotifications(userId);

        List<NotificationResponseDto> dtos = daos.stream().map(NotificationResponseDto::of).toList();

//        Map<Long, NotificationResponseDto> map = new HashMap<>();
//        dtos.forEach(dto -> map.put(dto.getQuestionId(), dto));

//        questionRepository.findAllById(daos.stream().map(NotificationDao::getQuestionId).toList())
//                .forEach(q->{
//                    NotificationResponseDto dto = map.get(q.getId());
//                    dto.setContent(NotificationType.getContent(dto.getType(), q.getContent()));
//                });

        notificationRepository.setReadAll(userId);
        return dtos;
    }

    @Transactional
    public Question getQuestionByNotification(Long userId, Long notificationId) {
        Optional<Notification> notificationOptional = notificationRepository.getNotificationQuestionById(notificationId);

        if(notificationOptional.isEmpty()) {
            throw new CustomException(ErrorCode.NOT_FOUND, "notification not found");
        }

        Notification notification = notificationOptional.get();

        if(notification.getReceiver().getId() != userId) {
            throw new CustomException(ErrorCode.BAD_REQUEST, "not notification receiver");
        }

        removeNotification(notification.getQuestion().getId());

        return notification.getQuestion();
    }

    private void removeNotification(Long questionId) {
        notificationRepository.removeByQuestion(questionId);
    }

    @Transactional
    public void addNotification(Long senderId, Long receiverId, NotificationType type, Long questionId) {
        Optional<Question> question = questionRepository.findById(questionId);

        if(question.isEmpty())
            throw new CustomException(ErrorCode.NOT_FOUND, "question not found");

        notificationRepository.save(
            Notification.builder()
                .notificationType(type)
                .question(question.get())
                .sender(entityManager.getReference(User.class, senderId))
                .receiver(entityManager.getReference(User.class, receiverId))
                .isRead(false)
                .build()
        );

        sendSSEData(emitterMap.get(receiverId), "1");
    }

    // Sse 관련 함수들
    public void sendUnReadNotificationCount(SseEmitter emitter, Long userId) {
        Integer count = notificationRepository.getUnReadNotificationsCount(userId);

        sendSSEData(emitter, count);
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
