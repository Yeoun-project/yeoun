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
    private final LocalDateTime createTime;

    public static CommentResponse of(
            final Comment comment,
            final Boolean isLike
    ) {
        return CommentResponse.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .isLike(isLike)
                .likeCount(comment.getLikeCount())
                .createTime(comment.getCreateTime())
                .build();
    }

}
