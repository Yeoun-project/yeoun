package yeoun.question.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AddQuestionRequest {

    @NotEmpty
    public String content;

    @NotNull
    public Long categoryId;

    public Long userId;

}
