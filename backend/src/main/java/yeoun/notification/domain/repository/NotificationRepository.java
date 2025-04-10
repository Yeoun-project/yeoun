package yeoun.notification.domain.repository;

import yeoun.notification.domain.Notification;
import yeoun.question.domain.Question;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    @Query("select n from Notification n where n.isRead=false and n.receiver.id = :userId")
    List<Notification> getAllNotifications(@Param("userId") Long userId);

    @Query("select n.question from Notification n where n.id = :notificationId")
    Question getNotificationQuestionById(@Param("notificationId") Long notificationId);

    @Query("update Notification n set n.isRead = true where n.receiver.id = :userId and n.id = :notificationId")
    int readNotification(@Param("userId") Long userId, @Param("notificationId") Long notificationId);
}
