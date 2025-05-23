package yeoun.question.dto.response;

import java.time.LocalDateTime;
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
    private final LocalDateTime createTime;
    private final Boolean isAuthor;
    private final Boolean isDeleted;

    public static QuestionDetailResponse of(
        final Question question,
        final Boolean isAuthor,
        final Boolean isDeleted
    ) {

        return QuestionDetailResponse.builder()
            .id(question.getId())
            .content(question.getContent())
            .commentCount(question.getComments().size())
            .categoryName(question.getCategory().getName())
            .createTime(question.getCreateTime())
            .isAuthor(isAuthor)
            .isDeleted(isDeleted)
            .build();
    }

}