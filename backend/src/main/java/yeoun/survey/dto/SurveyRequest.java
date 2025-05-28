package yeoun.survey.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SurveyRequest {

    @NotNull(message = "별점을 입력해주세요.")
    @DecimalMax(value = "5", message = "별점은 5점을 넘을 수 없습니다.")
    @DecimalMin(value = "0", message = "별점은 0점보다 낮을 수 없습니다.")
    private final double starRate;

    private String message;

}
