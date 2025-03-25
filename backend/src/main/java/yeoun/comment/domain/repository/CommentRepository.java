package yeoun.comment.domain.repository;

import yeoun.comment.domain.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

    @Modifying
    @Query(value = "update comment set content = :content where id = :commentId and user_id = :userId", nativeQuery = true)
    int update(@Param("commentId") Long commentId, @Param("content") String content, @Param("userId") Long userId);

    @Modifying
    @Query(value = "delete from comment where id = :commentId and user_id = :userId", nativeQuery = true)
    int delete(@Param("commentId") Long commentId, @Param("userId") Long userId);
}
