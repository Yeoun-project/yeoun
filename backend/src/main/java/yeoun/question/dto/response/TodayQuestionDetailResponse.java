package yeoun.question.dto.response;

import java.util.Date;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TodayQuestionDetailResponse {

    private Long id; // questionHistoryId

    private Date created_at;

    private String content;

    private String comment;

}
