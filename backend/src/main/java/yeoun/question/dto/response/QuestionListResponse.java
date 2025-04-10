package yeoun.question.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@Builder
@RequiredArgsConstructor
public class QuestionListResponse {

    final private List<QuestionResponse> questions;
    final private Boolean hasNext;

}
