package yeoun.question.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AddTodayQuestionCommentRequest {

    @NotNull(message = "질문 ID를 입력해주세요.")
    final private Long questionId;

    @NotEmpty(message = "댓글 내용을 입력해주세요.")
    @Size(max = 500, message = "댓글 내용은 500자를 초과할 수 없습니다.")
    final private String comment;

}
