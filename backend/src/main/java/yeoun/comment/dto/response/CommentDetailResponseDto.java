package yeoun.comment.dto.response;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentDetailResponseDto {

    private Long id;

    private String content;

    private String categoryName;

    private Date createTime;

    private CommentResponse comment;

}
