package yeoun.user.domain.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import yeoun.user.domain.User;

@Repository
public interface UserDeleteRepository extends JpaRepository<User, Long> {
    @Modifying
    @Query("update Comment c set c.content = '이미 떠나간 여운입니다!', c.user.id = null where c.user.id = :userId")
    void updateComment(@Param("userId") Long userId);

    @Modifying
    @Query("delete from Like l where l.user.id = :userId")
    void deleteLike(@Param("userId") Long userId);

    @Modifying
    @Query("delete from Notification n where n.receiver.id = :userId or n.sender.id = :userId or n.question.id in :questionId")
    void deleteNotification(@Param("userId") Long userId, @Param("questionId") List<Long> questionId);

    @Modifying
    @Query(value = "delete from user_history where user_id = :userId", nativeQuery = true)
    void deleteUserHistory(@Param("userId") Long userId);

    @Modifying
    @Query(value = "delete from comment where question_id in :questionId", nativeQuery = true)
    void deleteComment(@Param("userId") Long userId, @Param("questionId") List<Long> questionId);

    @Modifying
    @Query(value = "delete from question_history where question_id in :questionId or user_id = :userId", nativeQuery = true)
    void deleteQuestionHistory(@Param("userId") Long userId, @Param("questionId") List<Long> questionId);

    @Modifying
    @Query(value = "delete from question where user_id = :userId", nativeQuery = true)
    void deleteQuestion(@Param("userId") Long userId);

    @Modifying
    @Query(value = "delete from user where id = :userId", nativeQuery = true)
    void hardDeleteUser(@Param("userId") Long userId);

}
