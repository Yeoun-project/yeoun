package yeoun.comment.presentation;

import yeoun.common.SuccessResponse;
import yeoun.comment.dto.request.SaveCommentRequest;
import yeoun.auth.service.JwtService;
import yeoun.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{questionId}")
    public ResponseEntity<?> addComment(
            @PathVariable("questionId") Long questionId,
            @RequestBody SaveCommentRequest commentRequestDto) {

        // check user
        Long userId = JwtService.getUserIdFromAuthentication();

        commentRequestDto.setUserId(userId);
        commentRequestDto.setQuestionId(questionId);

        // add comment
        commentService.saveComment(commentRequestDto);

        // response
        return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResponse("Add comment success", null));
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<?> updateComment(@PathVariable("commentId") Long commentId, @RequestBody SaveCommentRequest commentRequestDto) {

        Long userId = JwtService.getUserIdFromAuthentication();

        commentRequestDto.setId(commentId);
        commentRequestDto.setUserId(userId);

        commentService.updateComment(commentRequestDto);

        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse("Updated comment success", null));
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable("commentId") Long commentId) {

        Long userId = JwtService.getUserIdFromAuthentication();

        commentService.deleteComment(commentId, userId);

        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse("deleted comment success", null));
    }

    // 신고하기
    @PostMapping("/report/{commentId}")
    public ResponseEntity<?> reportComment(@PathVariable("commentId") Long commentId) {
        // reportService.reportCommnet(commentId);
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse("not service", null));
    }

}
