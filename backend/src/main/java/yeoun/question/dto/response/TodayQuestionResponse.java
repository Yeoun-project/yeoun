package yeoun.question.dto.response;

import lombok.*;
import yeoun.question.domain.Question;

@Getter
@Builder
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class TodayQuestionResponse {

    final private Long id;
    final private String content;

    public static TodayQuestionResponse of(
            Question question
    ) {
        return TodayQuestionResponse.builder()
                .id(question.getId())
                .content(question.getContent())
                .build();
    }

}
