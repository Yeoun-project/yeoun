package yeoun.like.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yeoun.comment.domain.Comment;
import yeoun.comment.domain.repository.CommentRepository;
import yeoun.exception.CustomException;
import yeoun.exception.ErrorCode;
import yeoun.like.domain.Like;
import yeoun.like.domain.repository.LikeRepository;
import yeoun.like.dto.request.LikeRequest;
import yeoun.user.domain.repository.UserRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class LikeService {

    private final LikeRepository likeRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    public void update(final Long userId, final Long commentId, final LikeRequest likeRequest) {
        Comment comment = getCommentOrThrow(commentId);

        if (likeRequest.getIsLike()) {
            like(comment, userId);
        } else {
            unlike(comment, userId);
        }
    }

    private void like(final Comment comment, final Long userId) {
        if (isAlreadyLiked(userId, comment.getId())) {
            throw new CustomException(ErrorCode.ALREADY_EXIST, "해당 답변에 이미 좋아요를 눌렀습니다.");
        }

        comment.updateAddLike();
        likeRepository.save(Like.builder()
                .user(userRepository.getReferenceById(userId))
                .comment(comment)
                .build()
        );
    }

    private void unlike(final Comment comment, final Long userId) {
        comment.updateRemoveLike();
        likeRepository.findByUserIdAndCommentId(userId, comment.getId())
                .ifPresentOrElse(
                        likeRepository::delete,
                        () -> {
                            throw new CustomException(ErrorCode.INVALID_PARAMETER, "좋아요가 존재하지 않습니다.");
                        }
                );
    }

    private boolean isAlreadyLiked(final Long userId, final Long commentId) {
        return likeRepository.findByUserIdAndCommentId(userId, commentId).isPresent();
    }

    private Comment getCommentOrThrow(final Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_PARAMETER, "해당 답변이 존재하지 않습니다."));
    }
}