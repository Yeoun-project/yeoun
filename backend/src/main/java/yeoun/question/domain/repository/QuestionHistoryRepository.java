package yeoun.question.domain.repository;

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

    @Query("select qh from QuestionHistoryEntity qh left join fetch qh.question where qh.user.id = :userId order by qh.createdDateTime desc")
    List<QuestionHistoryEntity> findAllByUserId(@Param("userId") long userId);

    @Query("select qh from QuestionHistoryEntity qh "
        + "left join fetch qh.question "
        + "where qh.id = :questionHistoryId and qh.user.id = :userId")
    Optional<QuestionHistoryEntity> findByIdWithQuestionAndUser(@Param("userId") Long userId, @Param("questionHistoryId") Long questionHistoryId);
}
