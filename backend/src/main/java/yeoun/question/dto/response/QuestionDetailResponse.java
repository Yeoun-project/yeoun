package yeoun.question.dto.response;

import lombok.*;
import yeoun.question.domain.Question;

import java.time.LocalDateTime;

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
