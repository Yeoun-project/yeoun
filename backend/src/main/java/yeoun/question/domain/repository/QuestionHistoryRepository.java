package yeoun.question.domain.repository;

<<<<<<< HEAD
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import yeoun.question.domain.QuestionHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionHistoryRepository extends JpaRepository<QuestionHistoryEntity, Long> {

    // 오늘의 질문 조회
    @Query("""
            SELECT qh FROM QuestionHistoryEntity qh
            LEFT JOIN FETCH qh.question q
            WHERE DATE(qh.createdDateTime) = CURRENT_DATE and qh.user.id = :userId
            """)
    Optional<QuestionHistoryEntity> findTodayQuestion(@Param("userId") Long userId);

    @Query("select qh from QuestionHistoryEntity qh "
        + "left join fetch qh.question "
        + "left join fetch qh.user "
        + "where qh.id = :id")
    Optional<QuestionHistoryEntity> findById(@Param("id") Long id);

    @Query("""
        select qh from QuestionHistoryEntity qh 
        left join fetch qh.question q
        where qh.user.id = :userId and qh.comment is not null order by qh.createdDateTime desc
        """)
    List<QuestionHistoryEntity> findAllByUserId(@Param("userId") long userId);

    @Query("select qh from QuestionHistoryEntity qh "
        + "left join fetch qh.question "
        + "where qh.id = :questionHistoryId and qh.user.id = :userId")
    Optional<QuestionHistoryEntity> findByIdWithQuestionAndUser(@Param("userId") Long userId, @Param("questionHistoryId") Long questionHistoryId);
=======
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import yeoun.question.domain.QuestionHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface QuestionHistoryRepository extends JpaRepository<QuestionHistory, Long> {

    @Query("""
            SELECT h FROM QuestionHistory h
            WHERE h.question.id = :questionId
                AND h.user.id = :userId
                AND DATE(h.createTime) = CURRENT_DATE
            """)
    Optional<QuestionHistory> findTodayHistoryByQuestionIdAndUser(
            @Param("userId") Long userId,
            @Param("questionId") Long questionId
    );

    @Modifying
    @Query("""
            UPDATE QuestionHistory questionHistory
            SET questionHistory.comment=:comment
            WHERE questionHistory.question.id = :questionId
            """)
    void addCommentToTodayQuestion(
            @Param("questionId") final Long questionId,
            @Param("comment") final String comment
    );

>>>>>>> d9428e8662699a05123a5a72f56aeffa81e9b6ca
}
