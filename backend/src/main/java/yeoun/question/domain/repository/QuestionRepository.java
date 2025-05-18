package yeoun.question.domain.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import yeoun.question.domain.Question;

import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

    @Query("select q from Question q left join fetch q.user where q.id = :id")
    Optional<Question> findQuestionById(@Param("id") Long id);

    @Query("""
            SELECT CASE 
                WHEN COUNT(question) > 0 THEN true 
                ELSE false END
            FROM Question question
            WHERE question.user.id = :userId
            AND DATE(question.createTime) = CURRENT_DATE
            """)
    Boolean existsByUserIdAndToday(@Param("userId") Long userId);

    @Query("""
            SELECT q FROM Question q
            LEFT JOIN q.comments c
              ON c.createTime BETWEEN :start AND :end
            WHERE q.isFixed = false
            GROUP BY q
            ORDER BY COUNT(c) DESC, q.createTime ASC
        """)
    Slice<Question> findAllOrderByCommentsCount(
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end,
            Pageable pageable
    );

    @Query("""
            SELECT q FROM Question q
            LEFT JOIN q.comments c
              ON c.createTime BETWEEN :start AND :end
            WHERE q.isFixed = false
            AND q.category.name = :category
            GROUP BY q
            ORDER BY COUNT(c) DESC, q.createTime ASC
            """)
    Slice<Question> findAllByCategoryAndTodayComments(
            @Param("category") String category,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end,
            Pageable pageable
    );

    @Query("""
            SELECT question FROM Question question
            WHERE (question.category = :category OR :category = null)
                AND question.user.id = :userId
            """)
    Slice<Question> findByUserId(@Param("userId")Long userId, @Param("category") String category, Pageable pageable);

    // 이전에 조회된 적 없는 인기 질문들 중 랜덤 1개의 질문 조회
    @Query("""
            SELECT q FROM Question q
            LEFT JOIN q.comments c
            WHERE NOT EXISTS (
                SELECT h.question.id FROM QuestionHistory h
                WHERE h.user.id = :userId AND h.question.id = q.id
            )
            GROUP BY q.id HAVING COUNT(c.id) >= 15
            ORDER BY FUNCTION('UUID') LIMIT 1
            """)
    Optional<Question> findRandomPopularityQuestionExcludingHistory(@Param("userId") Long userId);

    @Query("""
            SELECT DISTINCT q FROM Question q
            JOIN q.comments c
            WHERE c.user.id = :userId
            AND (:category IS NULL OR q.category.name = :category)
            """)
    Slice<Question> findAllCommentedQuestionsByUserIdAndCategory(
            @Param("userId") Long userId,
            @Param("category") String category,
            Pageable pageable
    );

    @Query("select count(*) as count, q.category as category from Question q where DATE(q.createTime) = CURRENT_DATE group by q.category")
    List<CategoryResponseDao> findCategoriesWithCount();

    Optional<Question> findByIdAndIsFixedIsFalse(Long id);

    List<Long> findAllIdsByUserId(@Param("userId") final Long userId);

}
