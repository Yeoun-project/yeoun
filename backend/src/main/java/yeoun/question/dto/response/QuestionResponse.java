package yeoun.question.dto.response;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuestionResponse {

    private Long id;

    private String content;

    private int heart;

    private int commentCount;

    private String categoryName;

    private Date createTime;

}
