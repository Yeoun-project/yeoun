package yeoun.question.dto.response;

import java.time.LocalDateTime;
import lombok.*;
import yeoun.question.domain.Question;

@Getter
@Builder
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class TodayQuestionResponse {

    final private Long id;
    final private String content;
    final private LocalDateTime createTime;
    final private boolean hasComment;

    public static TodayQuestionResponse of(
            Question question,
            Boolean hasComment
    ) {
        return TodayQuestionResponse.builder()
                .id(question.getId())
                .content(question.getContent())
                .createTime(question.getCreateTime())
                .hasComment(hasComment)
                .build();
    }

}
