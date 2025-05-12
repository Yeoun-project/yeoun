package yeoun.question.dto.response;

import java.util.List;

import lombok.*;

@Getter
@RequiredArgsConstructor
public class TodayQuestionListResponse {

    private final List<TodayQuestionResponse> questions;

}
