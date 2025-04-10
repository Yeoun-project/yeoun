package yeoun.question.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;
import lombok.*;
import yeoun.question.domain.Question;

@Getter
@Builder
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class TodayQuestionResponse {

<<<<<<< HEAD
    private Long id; // questionHistoryId

    private Date created_at;

    private String content;

    @JsonProperty("isComment")
    private Boolean isComment;
=======
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
>>>>>>> d9428e8662699a05123a5a72f56aeffa81e9b6ca

}
