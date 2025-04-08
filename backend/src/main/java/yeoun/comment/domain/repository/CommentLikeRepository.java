package yeoun.comment.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import yeoun.comment.domain.CommentLikeEntity;

import java.util.List;

@Repository
public interface CommentLikeRepository extends JpaRepository<CommentLikeEntity, Long> {

    @Query("SELECT cl.comment.id FROM CommentLikeEntity cl WHERE cl.user.id = :userId AND cl.comment.id IN :commentIds")
    List<Long> findLikedCommentIdsByUser(@Param("userId") Long userId, @Param("commentIds") List<Long> commentIds);

}
