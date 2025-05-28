package yeoun.survey.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SurveyRequest {

    @NotNull(message = "별점을 입력해주세요.")
    @DecimalMax(value = "5", message = "별점은 5점을 넘을 수 없습니다.")
    @DecimalMin(value = "0", message = "별점은 0점보다 낮을 수 없습니다.")
    private final double starRate;

    @NotBlank(message = "메세지를 입력해주세요.")
    private final String message;

}
