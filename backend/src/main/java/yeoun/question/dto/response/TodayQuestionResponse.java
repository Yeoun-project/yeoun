package yeoun.question.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;
import lombok.*;
import yeoun.question.domain.Question;

@Getter
@Builder
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class TodayQuestionResponse {

    final private Long id;
    final private String content;
    final private Date created_at;

    public static TodayQuestionResponse of(Question question) {
        return TodayQuestionResponse.builder()
                .id(question.getId())
                .content(question.getContent())
                .build();
    }

}
