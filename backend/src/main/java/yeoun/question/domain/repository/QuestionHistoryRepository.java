package yeoun.question.domain.repository;

import java.util.List;
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
            """)
    Optional<QuestionHistory> findTodayHistoryByQuestionIdAndUser(
            @Param("userId") final Long userId,
            @Param("questionId") final Long questionId
    );

    @Modifying
    @Query("""
            UPDATE QuestionHistory questionHistory
            SET questionHistory.comment=:comment,
                questionHistory.commentTime=CURRENT_TIMESTAMP
            WHERE questionHistory.question.id = :questionId
                AND questionHistory.user.id = :userId
            """)
    void addCommentToTodayQuestion(
            @Param("userId") final Long userId,
            @Param("questionId") final Long questionId,
            @Param("comment") final String comment
    );

    @Query("select qh from QuestionHistory qh "
        + "left join fetch qh.question "
        + "left join fetch qh.user "
        + "where qh.id = :id")
    Optional<QuestionHistory> findById(@Param("id") Long id);

    @Query("""
        SELECT questionHistory FROM QuestionHistory questionHistory
        LEFT JOIN FETCH questionHistory.question question
        WHERE questionHistory.user.id = :userId
            AND questionHistory.comment IS NOT NULL
        ORDER BY questionHistory.createTime DESC
        """)
    List<QuestionHistory> findAllCommentedWithQuestionByUserId(@Param("userId") Long userId);

}
