package yeoun.question.domain.repository;

import yeoun.question.domain.QuestionEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<QuestionEntity, Long> {
    @Query("select q from QuestionEntity q left join fetch q.user where q.id = :id")
    Optional<QuestionEntity> findQuestionById(@Param("id") long id);

    @Query("SELECT q FROM QuestionEntity q LEFT JOIN q.comments c GROUP BY q ORDER BY COUNT(c) DESC")
    List<QuestionEntity> findAllOrderByCommentsCountDesc();

    @Query("SELECT q FROM QuestionEntity q LEFT JOIN FETCH q.comments left join fetch q.user WHERE q.id = :questionId")
    Optional<QuestionEntity> findQuestionWithCommentById(@Param("questionId") Long questionId);

    @Query("select q from QuestionEntity q left join fetch q.comments where q.user.id = :userId")
    List<QuestionEntity> findByUserId(@Param("userId")Long userId);

    // 이전에 조회된 적 없는 고정 질문들 중 랜덤 1개의 질문 조회
    @Query("""
            SELECT q FROM QuestionEntity q
            LEFT JOIN QuestionHistoryEntity h ON q.id = h.question.id AND h.user.id = :userId
            WHERE q.isFixed = true
            AND h.id IS NULL
            ORDER BY FUNCTION('UUID') LIMIT 1
            """)
    Optional<QuestionEntity> findRandomFixedQuestionExcludingHistory(@Param("userId") Long userId);

    // 오늘의 질문 조회
    @Query("""
            SELECT q FROM QuestionEntity q
            LEFT JOIN QuestionHistoryEntity h ON q.id = h.question.id AND h.user.id = :userId
            WHERE DATE(h.createdDateTime) = CURRENT_DATE
            """)
    Optional<QuestionEntity> findTodayQuestion(@Param("userId") Long userId);

    // 이전에 조회된 적 없는 인기 질문들 중 랜덤 1개의 질문 조회
    @Query("""
            SELECT q FROM QuestionEntity q
            LEFT JOIN q.comments c
            WHERE NOT EXISTS (
                SELECT h.question.id FROM QuestionHistoryEntity h
                WHERE h.user.id = :userId AND h.question.id = q.id
            )
            GROUP BY q.id HAVING COUNT(c.id) >= 10
            ORDER BY FUNCTION('UUID') LIMIT 1
            """)
    Optional<QuestionEntity> findRandomPopularityQuestionExcludingHistory(@Param("userId") Long userId);

    Optional<QuestionEntity> getQuestionEntityById(Long id);
}
