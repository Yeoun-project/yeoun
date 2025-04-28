package yeoun.comment.presentation;

import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import yeoun.comment.dto.response.CommentListResponse;
import yeoun.common.SuccessResponse;
import yeoun.comment.dto.request.SaveCommentRequest;
import yeoun.auth.service.JwtService;
import yeoun.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/all/{questionId}")
    public ResponseEntity<SuccessResponse> getAllComments(
            @PathVariable("questionId") Long questionId,
            @PageableDefault(sort = "createTime", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        Long userId = JwtService.getUserIdFromAuthentication();
        CommentListResponse commentListResponse = commentService.getAllComments(questionId, userId, pageable);
        return ResponseEntity.ok(
                new SuccessResponse("댓글 목록 조회를 성공했습니다.", commentListResponse));
    }

    @PostMapping("/{questionId}")
    public ResponseEntity<?> addComment(
            @PathVariable("questionId") Long questionId,
            @RequestBody @Valid SaveCommentRequest commentRequestDto
    ) {
        Long userId = JwtService.getUserIdFromAuthentication();
        commentRequestDto.setUserId(userId);
        commentRequestDto.setQuestionId(questionId);
        commentService.saveComment(commentRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new SuccessResponse("답변 추가를 성공했습니다.", null));
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<?> updateComment(
            @PathVariable("commentId") Long commentId,
            @RequestBody @Valid SaveCommentRequest commentRequestDto
    ) {

        Long userId = JwtService.getUserIdFromAuthentication();

        commentRequestDto.setId(commentId);
        commentRequestDto.setUserId(userId);

        commentService.updateComment(commentRequestDto);

        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse("답변 수정을 성공했습니다.", null));
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable("commentId") Long commentId) {

        Long userId = JwtService.getUserIdFromAuthentication();

        commentService.deleteComment(commentId, userId);

        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse("답변 삭제를 성공했습니다.", null));
    }

    // 신고하기
    @PostMapping("/report/{commentId}")
    public ResponseEntity<?> reportComment(@PathVariable("commentId") Long commentId) {
        // reportService.reportCommnet(commentId);
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse("not service", null));
    }

}
