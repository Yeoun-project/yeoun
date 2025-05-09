package yeoun.notification.service;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.transaction.annotation.Transactional;
import yeoun.notification.domain.NotificationType;
import yeoun.notification.domain.Notification;
import yeoun.notification.dto.response.NotificationDetailResponse;
import yeoun.notification.dto.response.NotificationListResponse;
import yeoun.question.domain.Question;
import yeoun.question.domain.repository.QuestionRepository;
import yeoun.question.dto.response.QuestionDetailResponse;
import yeoun.user.domain.User;
import yeoun.exception.CustomException;
import yeoun.exception.ErrorCode;
import yeoun.notification.domain.repository.NotificationRepository;
import jakarta.persistence.EntityManager;
import java.io.IOException;
import java.util.HashMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import yeoun.user.domain.repository.UserRepository;
import yeoun.user.service.UserService;

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
    private final UserService userService;
    private final UserRepository userRepository;

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

    @Transactional(readOnly = true)
    public NotificationListResponse getAllNotifications(Long userId, Pageable pageable) {
        Slice<Notification> notifications = notificationRepository.findAllNotifications(userId, pageable);
        List<NotificationDetailResponse> notificationDetailResponses = notifications.stream()
                .map(NotificationDetailResponse::of)
                .toList();
        NotificationListResponse notificationListResponse = new NotificationListResponse(notificationDetailResponses, notifications.hasNext());

        notificationRepository.setReadAll(userId);
        sendUnReadNotificationCount(userId);

        return notificationListResponse;
    }

    @Transactional
    public QuestionDetailResponse getQuestionFromNotification(Long userId, Long questionId) {
        Question question = notificationRepository.getQuestion(userId, questionId).orElseThrow(
            () -> new CustomException(ErrorCode.NOT_FOUND, "no notification with question id")
        );

        notificationRepository.removeByQuestion(userId, questionId);

        boolean isAuthor = false;
        if (question.getUser() != null) isAuthor = question.getUser().getId().equals(userId);

        boolean isDeleted = question.getDeleteTime()==null;

        return QuestionDetailResponse.of(question, isAuthor, isDeleted);
    }

    @Transactional
    public void addNotification(Long senderId, Long receiverId, NotificationType type, Long questionId) {
        if(receiverId == null) return;

        Question question = questionRepository.findById(questionId).orElseThrow(
            () -> {throw new CustomException(ErrorCode.NOT_FOUND, "question not found");}
        );

        if(type.equals(NotificationType.COMMENT_LIKE)){
            // get old from db where receiverId, questionId, type
            Optional<Notification> old = notificationRepository.findOldNotification(receiverId, questionId, type.toString());
            if(old.isPresent()) {
                notificationRepository.upCountAndUnRead(old.get().getId());
                return;
            }
        }

        //save new notification
        Notification noti = notificationRepository.save(
            Notification.builder()
                .notificationType(type)
                .question(question)
                .sender(entityManager.getReference(User.class, senderId))
                .receiver(entityManager.getReference(User.class, receiverId))
                .isRead(false)
                .build()
        );

        sendUnReadNotificationCount(receiverId);
    }

    // Sse 관련 함수들
    public void sendUnReadNotificationCount(Long userId) {
        //Integer count = notificationRepository.getUnReadNotificationsCount(userId); // 개수를 반환암함 (유무만 반환)
        Optional<User> optionalUser = userRepository.findById(userId);

        if(optionalUser.isEmpty()) return; // 받을 사람이 없는 경우 : delete 된경우 아무 notification을 발생 시키지 않음

        User user = optionalUser.get();

        if(user.getIsNotification())
            sendSSEData(emitterMap.get(userId), 1);
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