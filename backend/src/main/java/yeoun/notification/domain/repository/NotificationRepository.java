package yeoun.notification.domain.repository;

import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.Modifying;
import yeoun.notification.domain.Notification;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import yeoun.question.domain.Question;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    @Query("select n from Notification n where n.receiver.id = :userId order by n.createTime")
    Slice<Notification> findAllNotifications(@Param("userId") Long userId, Pageable pageable);

    @Query("select n.question from Notification n where n.receiver.id=:userId and n.question.id=:questionId")
    Optional<Question> getQuestion(@Param("userId") Long userId, @Param("questionId") Long questionId);

    @Modifying
    @Query("update Notification n set n.isRead = true where n.receiver.id = :userId")
    void setReadAll(@Param("userId")Long userId);

    @Modifying
    @Query("delete from Notification n where n.question.id = :questionId and n.receiver.id=:userId")
    void removeByQuestion(@Param("userId")Long userId, @Param("questionId") Long questionId);

    @Query("select n from Notification n where n.receiver.id = :receiverId and n.question.id=:questionId and n.notificationType = :type")
    Optional<Notification> findOldNotification(@Param("receiverId") Long receiverId, @Param("questionId") Long questionId, @Param("type") String type);

    @Modifying
    @Query("update Notification n set n.isRead=false, n.count=n.count+1 where n.id=:id")
    void upCountAndUnRead(@Param("id") Long id);


    @Query("select count(*) from Notification n where n.isRead=false and n.receiver.id = :userId")
    Integer getUnReadNotificationsCount(@Param("userId") Long userId);

}