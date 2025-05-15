package yeoun.question.dto.response;

import java.time.LocalDateTime;
import lombok.*;
import yeoun.question.domain.Question;
import yeoun.question.domain.QuestionHistory;

@Getter
@Builder
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class TodayQuestionResponse {

    final private Long id;
    final private String content;
    final private LocalDateTime createTime;
    final private boolean hasComment;

    public static TodayQuestionResponse of(
            QuestionHistory questionHistory,
            Boolean hasComment
    ) {
        final Question question = questionHistory.getQuestion();

        return TodayQuestionResponse.builder()
                .id(question.getId())
                .content(question.getContent())
                .createTime(questionHistory.getCreateTime())
                .hasComment(hasComment)
                .build();
    }

}
