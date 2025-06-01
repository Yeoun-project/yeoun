package yeoun.comment.dto.response;

import lombok.*;
import yeoun.comment.domain.Comment;

import java.time.LocalDateTime;

@Getter
@Builder
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class CommentResponse {

    private final Long id;
    private final String content;
    private final Long likeCount;
    private final Boolean isLike;
    private final Boolean isDeleted;
    private final LocalDateTime createTime;

    public static CommentResponse of(
            final Comment comment,
            final Boolean isLike
    ) {
        System.out.println("시발 진짜 너무 하네 씨발 씨발 씨발"
            + comment.getUser().getId());
        return CommentResponse.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .isLike(isLike)
                .likeCount(comment.getLikeCount())
                .createTime(comment.getCreateTime())
                .isDeleted(comment.getUser() == null)
                .build();
    }

}
