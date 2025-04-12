package yeoun.comment.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class CommentListResponse {

    final CommentResponse myComment;
    final List<CommentResponse> comments;
    final Boolean hasNextPage;

}
