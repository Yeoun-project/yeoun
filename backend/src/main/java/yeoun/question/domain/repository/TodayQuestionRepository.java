package yeoun.question.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import yeoun.question.domain.Question;
import yeoun.question.domain.QuestionHistory;

import java.util.Optional;

@Repository
public interface TodayQuestionRepository extends JpaRepository<Question, Long> {

    // 이전에 조회된 적 없는 고정 질문들 중 랜덤 1개의 질문 조회
    @Query("""
            SELECT question FROM Question question
            LEFT JOIN QuestionHistory questionHistory
                ON question.id = questionHistory.question.id
                AND questionHistory.user.id = :userId
            WHERE question.isFixed = true
                AND questionHistory.id IS NULL
            ORDER BY FUNCTION('UUID') LIMIT 1
            """)
    Optional<Question> findRandomFixedQuestionExcludingHistory(@Param("userId") Long userId);

    // 오늘의 질문 조회
    @Query("""
            SELECT questionHistory FROM QuestionHistory questionHistory
            JOIN FETCH questionHistory.question
            WHERE questionHistory.user.id = :userId
                AND DATE(questionHistory.createTime) = CURRENT_DATE
            """)
    Optional<QuestionHistory> findQuestionHistoryWithQuestionByUserId(@Param("userId") Long userId);

    @Query("""
            SELECT questionHistory FROM QuestionHistory questionHistory
            JOIN FETCH questionHistory.question
            JOIN FETCH questionHistory.user
            WHERE questionHistory.question = :questionId
                AND questionHistory.user = :userId
            """)
    Optional<QuestionHistory> findByQuestionIdAndUserId(
            @Param("questionId") final Long questionId,
            @Param("userId") final Long userId
    );

}
