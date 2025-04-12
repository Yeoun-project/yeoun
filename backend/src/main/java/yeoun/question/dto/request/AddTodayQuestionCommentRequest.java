package yeoun.question.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AddTodayQuestionCommentRequest {

    @NotNull
    final private Long questionId;

    @NotEmpty
    final private String comment;

}
