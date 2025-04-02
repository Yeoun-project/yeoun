package yeoun.question.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TodayQuestionResponse {

    private Long id;

    private String content;

    // 추후 필요한 요구 데이터 확장을 위해 DTO 분리

}
