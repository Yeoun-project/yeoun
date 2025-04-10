package yeoun.question.dto.response;

import java.util.Date;
import lombok.*;
import yeoun.question.domain.Question;

import java.time.LocalDateTime;

@Getter
@Builder
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class QuestionResponse {

    final private Long id;
    final private String content;
    final private int commentCount;
    final private String categoryName;
    final private Date createTime;

    public static QuestionResponse of(
        Question question
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