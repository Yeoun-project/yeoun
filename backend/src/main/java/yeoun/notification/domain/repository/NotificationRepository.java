package yeoun.notification.domain.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.Modifying;
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
    List<Notification> getUnReadNotifications(@Param("userId") Long userId);

    @Modifying
    @Query("update Notification n set n.isRead = true where n.id in :notificationIds")
    void setReadAll(@Param("notificationIds")List<Long> notificationIds);

    @Query("select n from Notification n left join fetch n.question left join fetch n.receiver where n.id = :notificationId")
    Optional<Notification> getNotificationQuestionById(@Param("notificationId") Long notificationId);

    @Query("select n from Notification n where n.receiver.id = :userId")
    List<Notification> getAllNotifications(@Param("userId") Long userId);

    void removeById(Long id);
}
