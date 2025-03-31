package yeoun.comment.domain.repository;

import java.util.List;
import java.util.Optional;
import yeoun.comment.domain.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

    @Query("select c from CommentEntity c where c.user.id = :userId and c.question.id = :questionId")
    Optional<CommentEntity> getCommentByUserId(@Param("userId") Long userId, @Param("questionId")Long questionId);

    @Query("select c from CommentEntity c left join fetch c.user left join fetch c.question where c.id = :id")
    Optional<CommentEntity> getCommentById(@Param("id") Long id);

    @Modifying
    @Query("delete from CommentEntity c where c.id = :id")
    void deleteById(@Param("id") Long id);

    @Query("select c from CommentEntity c left join fetch c.question where c.user.id = :userId")
    List<CommentEntity> getCommentsById(@Param("userId") Long userId);
}
