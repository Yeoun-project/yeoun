package yeoun.comment.domain.repository;

import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import yeoun.comment.domain.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("SELECT c FROM Comment c WHERE c.user.id = :userId AND c.question.id = :questionId")
    Optional<Comment> getCommentByUserId(@Param("userId") Long userId, @Param("questionId")Long questionId);

    @Query("SELECT c FROM Comment c LEFT JOIN FETCH c.user LEFT JOIN FETCH c.question WHERE c.id = :id")
    Optional<Comment> getCommentById(@Param("id") Long id);

    @Modifying
    @Query("DELETE FROM Comment c WHERE c.id = :id")
    void deleteById(@Param("id") Long id);

    @Query("""
            SELECT c FROM Comment c
            WHERE c.question.id = :questionId
            AND c.user.id <> :userId
            """)
    Slice<Comment> getAllCommentByQuestionExcludeMyself(
            @Param("questionId") Long questionId,
            @Param("userId") Long userId,
            Pageable pageable
    );

    Optional<Comment> findTopByUserIdAndQuestionId(@Param("userId") Long userId, @Param("questionId") Long questionId);
}
