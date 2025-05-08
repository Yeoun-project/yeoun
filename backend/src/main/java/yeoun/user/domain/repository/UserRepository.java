package yeoun.user.domain.repository;

import java.time.LocalDateTime;
import java.util.List;
import yeoun.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE u.oAuthId = :oAuthId")
    Optional<User> findByOAuthId(@Param("oAuthId") String oAuthId);

    @Query("update User as u set u.uuid = :uuid where u.id = :userId")
    String updateUUID(@Param("userId") String userId, @Param("uuid") String uuid);


    // ------------- 회원 탈퇴 (모든 정보 삭제) ----------
    // table 삭제 순서 : like(userId) -> notification(receiverId, questionId) -> userHistory(userId) -> comment(question_id) -> question_history(user_id, question_id) -> question(user_id) -> user
    // 변경 필요 : comment(user_id)
    //  그냥 둠 : like(comment_id)
    // 변경(comment(user_id)) -> 삭제 (생략)

    @Modifying
    @Query("update Comment c set c.content = '이미 떠나간 여운입니다!', c.user.id = null where c.user.id = :userId")
    void updateComment(@Param("userId") Long userId);

    @Modifying
    @Query(value = "delete from `like` where user_id = :userId", nativeQuery = true)
    void deleteLike(@Param("userId") Long userId);

    @Modifying
    @Query(value = "delete from notification where receiver_id = :userId or question_id in :questionId", nativeQuery = true)
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

