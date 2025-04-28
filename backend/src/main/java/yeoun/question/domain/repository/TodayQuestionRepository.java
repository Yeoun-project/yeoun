package yeoun.question.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import yeoun.question.domain.Question;

import java.util.Optional;

@Repository
public interface TodayQuestionRepository extends JpaRepository<Question, Long> {

    // 이전에 조회된 적 없는 고정 질문들 중 랜덤 1개의 질문 조회
    @Query("""
            SELECT q FROM Question q
            LEFT JOIN QuestionHistory h ON q.id = h.question.id AND h.user.id = :userId
            WHERE q.isFixed = true
            AND h.id IS NULL
            ORDER BY FUNCTION('UUID') LIMIT 1
            """)
    Optional<Question> findRandomFixedQuestionExcludingHistory(@Param("userId") Long userId);

    // 오늘의 질문 조회
    @Query("""
            SELECT q FROM Question q
            LEFT JOIN QuestionHistory h ON q.id = h.question.id AND h.user.id = :userId
            WHERE DATE(h.createTime) = CURRENT_DATE
            """)
    Optional<Question> findTodayQuestion(@Param("userId") Long userId);

    @Query("""
        SELECT CASE WHEN h.comment IS NOT NULL THEN true ELSE false END
        FROM QuestionHistory h
        WHERE h.question.id = :questionId
        AND h.user.id = :userId
        """)
    boolean existsCommentByQuestionIdAndUserId(@Param("questionId") Long questionId, @Param("userId") Long userId);

}
