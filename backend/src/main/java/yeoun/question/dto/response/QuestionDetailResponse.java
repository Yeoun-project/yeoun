package yeoun.question.dto.response;

import java.util.Date;
import lombok.*;
import yeoun.question.domain.Question;

@Getter
@Builder
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class QuestionDetailResponse {

    private final Long id;
    private final String content;
    private final int commentCount;
    private final String categoryName;
    private final Date createTime;
    private final Boolean isAuthor;

    public static QuestionDetailResponse of(
        final Question question,
        final Boolean isAuthor
    ) {

        return QuestionDetailResponse.builder()
            .id(question.getId())
            .content(question.getContent())
            .commentCount(question.getComments().size())
            .categoryName(question.getCategory().getName())
            .createTime(question.getCreateTime())
            .isAuthor(isAuthor)
            .build();
    }

}