package yeoun.question.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import yeoun.comment.dto.response.CommentResponse;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class QuestionDetailResponse {

    private Long id;

    private String content;

    private int heart;

    private int commentCount;

    private String categoryName;

    private Date createTime;

    private List<CommentResponse> comments;

    @Builder
    public QuestionDetailResponse(Long id, String content, int heart,int commentCount, String categoryName,
                                  Date createTime, List<CommentResponse> comments) {
        this.id = id;
        this.content = content;
        this.heart = heart;
        this.commentCount = commentCount;
        this.categoryName = categoryName;
        this.createTime = createTime;
        this.comments = comments;
    }
}
