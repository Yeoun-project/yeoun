package yeoun.question.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TodayQuestionResponse {

    private Long id; // questionHistoryId

    private Date created_at;

    private String content;

    @JsonProperty("isComment")
    private Boolean isComment;

}
