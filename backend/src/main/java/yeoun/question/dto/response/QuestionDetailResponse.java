package yeoun.question.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class QuestionDetailResponse {

    private Long id;

    private String content;

    private int commentCount;

    private String categoryName;

    private Date createTime;

    @JsonProperty("isAuthor")
    private boolean isAuthor;

    @Builder
    public QuestionDetailResponse(Long id, String content,int commentCount, String categoryName,
                                  Date createTime, boolean isAuthor) {
        this.id = id;
        this.content = content;
        this.commentCount = commentCount;
        this.categoryName = categoryName;
        this.createTime = createTime;
        this.isAuthor = isAuthor;
    }
}
