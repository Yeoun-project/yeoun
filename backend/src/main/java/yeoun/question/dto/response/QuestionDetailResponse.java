package yeoun.question.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class QuestionDetailResponse {

    private Question question;

    private MyComment myComment;

    private List<Comment> comments;

    @Getter
    @Builder
    @AllArgsConstructor
    public static class Question {

        private Long id;

        private String content;

        private int heart;

        private int commentCount;

        private String categoryName;

        private Date createTime;

        @JsonProperty("isAuthor")
        private boolean author;

    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class MyComment {

        private Long id;

        private String content;

        private Date createTime;

    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class Comment {

        private Long id;

        private String content;

        private Date createTime;

        @JsonProperty("isLike")
        private boolean like;

    }

}
