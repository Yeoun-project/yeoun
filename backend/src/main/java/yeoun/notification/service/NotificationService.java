package yeoun.notification.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.beans.factory.annotation.Value;
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
import yeoun.question.service.QuestionService;
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

    private final NotificationRepository notificationRepository;
    private final QuestionRepository questionRepository;
    private final EntityManager entityManager;
    private final QuestionService questionService;
    private final SseServiceInterface sseService;
    private final UserRepository userRepository;

    public SseEmitter getConnect(Long userId) {
        return sseService.getSseEmitter(userId, notificationRepository.getUnReadNotificationsCount(userId));
    }

    @Transactional
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
        notificationRepository.removeByQuestion(userId, questionId);
        return questionService.getQuestionDetail(userId, questionId);
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

    private void sendUnReadNotificationCount(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);

        if(optionalUser.isEmpty()) return;

        User user = optionalUser.get();

        if(user.getIsNotification())
            sseService.sendData(userId, "notification", notificationRepository.getUnReadNotificationsCount(userId));
    }

}
