package yeoun.question.domain.repository;

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
            @Param("questionId") Long questionId,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
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

}
