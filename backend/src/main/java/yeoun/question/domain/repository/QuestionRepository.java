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
import yeoun.question.dto.response.CategoryResponseDto;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    @Query("select q from Question q left join fetch q.user where q.id = :id")
    Optional<Question> findQuestionById(@Param("id") Long id);

<<<<<<< HEAD
    @Query("SELECT q FROM QuestionEntity q LEFT JOIN q.comments c where q.isFixed = false GROUP BY q ORDER BY COUNT(c) DESC")
    List<QuestionEntity> findAllOrderByCommentsCountDesc();
=======
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
>>>>>>> d9428e8662699a05123a5a72f56aeffa81e9b6ca

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

    @Query("select q from Question q left join fetch q.comments where q.user.id = :userId")
    List<Question> findByUserId(@Param("userId")Long userId);

    // 이전에 조회된 적 없는 고정 질문들 중 랜덤 1개의 질문 조회
    @Query("""
            SELECT q FROM Question q
            LEFT JOIN QuestionHistory h ON q.id = h.question.id AND h.user.id = :userId
            WHERE q.isFixed = true
            AND h.id IS NULL
            ORDER BY FUNCTION('UUID') LIMIT 1
            """)
    Optional<Question> findRandomFixedQuestionExcludingHistory(@Param("userId") Long userId);

<<<<<<< HEAD
=======
    // 오늘의 질문 조회
    @Query("""
            SELECT q FROM Question q
            LEFT JOIN QuestionHistory h ON q.id = h.question.id AND h.user.id = :userId
            WHERE DATE(h.createTime) = CURRENT_DATE
            """)
    Optional<Question> findTodayQuestion(@Param("userId") Long userId);

>>>>>>> d9428e8662699a05123a5a72f56aeffa81e9b6ca
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

<<<<<<< HEAD
    @Query("select count(*) as count, q.category as category from QuestionEntity q where DATE(q.createdDateTime) = CURRENT_DATE group by q.category")
    List<CategoryResponseDao> findCategoriesWithCount();

    @Query("select q from QuestionEntity q where q.category.id = :categoryId and q.isFixed = false")
    List<QuestionEntity> findAllByCategoryId(@Param("categoryId") Long categoryId);

=======
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
>>>>>>> d9428e8662699a05123a5a72f56aeffa81e9b6ca
}
