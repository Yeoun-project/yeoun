package yeoun.question.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
public class AddQuestionRequest {

    @NotEmpty(message = "질문 내용을 입력해주세요.")
    private final String content;

    @NotNull(message = "카테고리 ID를 입력해주세요.")
    private final Long categoryId;

    private Long userId;

}
