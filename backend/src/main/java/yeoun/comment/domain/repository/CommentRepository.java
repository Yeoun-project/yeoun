package yeoun.comment.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import yeoun.comment.domain.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

    @Query("SELECT c FROM CommentEntity c WHERE c.user.id = :userId AND c.question.id = :questionId")
    Optional<CommentEntity> getCommentByUserId(@Param("userId") Long userId, @Param("questionId")Long questionId);

    @Query("SELECT c FROM CommentEntity c LEFT JOIN FETCH c.user LEFT JOIN FETCH c.question WHERE c.id = :id")
    Optional<CommentEntity> getCommentById(@Param("id") Long id);

    @Modifying
    @Query("DELETE FROM CommentEntity c WHERE c.id = :id")
    void deleteById(@Param("id") Long id);

    @Query("""
            SELECT c FROM CommentEntity c
            WHERE c.question.id = :questionId
            AND c.user.id <> :userId
            """)
    Slice<CommentEntity> getAllCommentByQuestionExcludeMyself(
            @Param("questionId") Long questionId,
            @Param("userId") Long userId,
            Pageable pageable
    );

    Optional<CommentEntity> findTopByUserIdAndQuestionId(@Param("userId") Long userId, @Param("questionId") Long questionId);
}
