package yeoun.question.domain.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import yeoun.question.domain.QuestionEntity;

import java.time.LocalDateTime;
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

    @Query("""
            SELECT q FROM QuestionEntity q
            LEFT JOIN q.comments c
              ON c.createTime BETWEEN :start AND :end
            WHERE q.isFixed = false
            GROUP BY q
            ORDER BY COUNT(c) DESC, q.createTime ASC
        """)
    Slice<QuestionEntity> findAllOrderByCommentsCount(
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end,
            Pageable pageable
    );

    @Query("""
            SELECT q FROM QuestionEntity q
            LEFT JOIN q.comments c
              ON c.createTime BETWEEN :start AND :end
            WHERE q.isFixed = false
            AND q.category.name = :category
            GROUP BY q
            ORDER BY COUNT(c) DESC, q.createTime ASC
            """)
    Slice<QuestionEntity> findAllByCategoryAndTodayComments(
            @Param("category") String category,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end,
            Pageable pageable
    );

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
            WHERE DATE(h.createTime) = CURRENT_DATE
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
            GROUP BY q.id HAVING COUNT(c.id) >= 15
            ORDER BY FUNCTION('UUID') LIMIT 1
            """)
    Optional<QuestionEntity> findRandomPopularityQuestionExcludingHistory(@Param("userId") Long userId);

    @Query("""
            SELECT DISTINCT q FROM QuestionEntity q
            JOIN q.comments c
            WHERE c.user.id = :userId
            AND (:category IS NULL OR q.category.name = :category)
            """)
    Slice<QuestionEntity> findAllCommentedQuestionsByUserIdAndCategory(
            @Param("userId") Long userId,
            @Param("category") String category,
            Pageable pageable
    );
}
