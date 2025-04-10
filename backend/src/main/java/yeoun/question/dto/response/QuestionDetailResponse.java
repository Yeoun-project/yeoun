package yeoun.question.dto.response;

import lombok.*;
import yeoun.question.domain.Question;

import java.time.LocalDateTime;

@Getter
@Builder
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class QuestionDetailResponse {

    private final Long id;
    private final String content;
    private final int commentCount;
    private final String categoryName;
    private final LocalDateTime createTime;
    private final Boolean isAuthor;

    public static QuestionDetailResponse of(
            final Question question,
            final Boolean isAuthor
    ) {

<<<<<<< HEAD
    private int heart;

    private int commentCount;

    private String categoryName;

    private Date createTime;

    @JsonProperty("isAuthor")
    private boolean isAuthor;

    private CommentResponse myComment;

    private List<CommentResponse> comments;

    @Builder
    public QuestionDetailResponse(Long id, String content, int heart,int commentCount, String categoryName,
                                  Date createTime, boolean isAuthor, List<CommentResponse> comments) {
        this.id = id;
        this.content = content;
        this.heart = heart;
        this.commentCount = commentCount;
        this.categoryName = categoryName;
        this.createTime = createTime;
        this.isAuthor = isAuthor;
        this.comments = comments;
=======
        return QuestionDetailResponse.builder()
                .id(question.getId())
                .content(question.getContent())
                .commentCount(question.getComments().size())
                .categoryName(question.getCategory().getName())
                .createTime(question.getCreateTime())
                .isAuthor(isAuthor)
                .build();
>>>>>>> d9428e8662699a05123a5a72f56aeffa81e9b6ca
    }

}
