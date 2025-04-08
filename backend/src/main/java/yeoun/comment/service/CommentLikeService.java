package yeoun.comment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import yeoun.comment.domain.repository.CommentLikeRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CommentLikeService {

    private final CommentLikeRepository commentLikeRepository;

    public Set<Long> getLikedCommentIdSetByUser(Long userId, List<Long> commentIds) {
        List<Long> likedIds = commentLikeRepository.findLikedCommentIdsByUser(userId, commentIds);
        return new HashSet<>(likedIds);
    }

}
