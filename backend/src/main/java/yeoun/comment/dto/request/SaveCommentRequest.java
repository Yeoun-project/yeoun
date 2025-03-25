package yeoun.comment.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SaveCommentRequest {

    public Long id;

    public String content;

    public Long userId;

    public Long questionId;

}
