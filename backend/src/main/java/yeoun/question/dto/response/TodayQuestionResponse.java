package yeoun.question.dto.response;

import java.util.Date;
import lombok.*;
import yeoun.comment.dto.response.CommentResponse;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TodayQuestionResponse {

    private Long id; // questionHistoryId

    private Date created_at;

    private String content;

    private String comment;

}
