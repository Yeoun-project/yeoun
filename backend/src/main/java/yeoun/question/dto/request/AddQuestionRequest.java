package yeoun.question.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
public class AddQuestionRequest {

    @NotEmpty(message = "질문 내용을 입력해주세요.")
    private final String content;

    @NotNull(message = "카테고리 ID를 입력해주세요.")
    private final Long categoryId;

    private Long userId;

    public AddQuestionRequest(@JsonProperty("content") String content, @JsonProperty("categoryId") Long categoryId) {
        this.content = content;
        this.categoryId = categoryId;
    }

}
