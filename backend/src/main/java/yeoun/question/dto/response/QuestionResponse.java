package yeoun.question.dto.response;

import lombok.*;
import org.aspectj.weaver.patterns.TypePatternQuestions;
import yeoun.question.domain.QuestionEntity;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Builder
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class QuestionResponse {

    final private Long id;
    final private String content;
    final private int commentCount;
    final private String categoryName;
    final private LocalDateTime createTime;

    public static QuestionResponse of(
            QuestionEntity question
    ) {
        return QuestionResponse.builder()
                .id(question.getId())
                .content(question.getContent())
                .commentCount(question.getComments().size())
                .categoryName(question.getCategory().getName())
                .createTime(question.getCreateTime())
                .build();
    }

}
