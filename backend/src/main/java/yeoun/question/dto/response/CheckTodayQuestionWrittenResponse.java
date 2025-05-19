package yeoun.question.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CheckTodayQuestionWrittenResponse {

    private final boolean hasWritten;

}
