package yeoun.question.dto.response;

import java.time.LocalDateTime;
import lombok.*;
import yeoun.question.domain.Question;
import yeoun.question.domain.QuestionHistory;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TodayQuestionDetailResponse {

    private final Long id;
    private final String content;
    private final LocalDateTime createTime;
    private Comment comment;

    public static TodayQuestionDetailResponse of(
            QuestionHistory questionHistory
    ) {
        Question todayQuestion = questionHistory.getQuestion();
        Comment comment = Comment.builder()
                .id(questionHistory.getId())
                .content(questionHistory.getComment())
                .createTime(questionHistory.getCommentTime())
                .updateTime(questionHistory.getUpdateTime())
                .build();

        return TodayQuestionDetailResponse.builder()
                .id(todayQuestion.getId())
                .content(todayQuestion.getContent())
                .createTime(questionHistory.getCreateTime())
                .comment(comment)
                .build();
    }

    @Getter
    @Builder
    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Comment {
        private final Long id;
        private final String content;
        private final LocalDateTime createTime;
        private final LocalDateTime updateTime;
    }

}
