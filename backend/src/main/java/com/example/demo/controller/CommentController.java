package com.example.demo.controller;

import com.example.demo.dto.request.AddCommentRequestDto;
import com.example.demo.jwt.JwtUtil;
import com.example.demo.service.CommentService;
import com.example.demo.util.CookieUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/comment")
@RequiredArgsConstructor
public class CommentController {
    @Value("${database.guest.id}")
    private String guestId;

    @Value("${jwt.comment-token-expiration-time}")
    private Long commentExpirationTime;

    private final static String commentToken = "comment";

    private final CommentService commentService;
    private final JwtUtil jwtUtil;

    @GetMapping("/${questionId}")
    public ResponseEntity<?> getComments(@PathVariable Long questionId) {
        return ResponseEntity.status(HttpStatus.OK).body(commentService.getAllComments(questionId));
    }

    @PostMapping("/add/${questionId}")
    public ResponseEntity<?> addComment(
            @PathVariable Long questionId,
            @RequestBody AddCommentRequestDto commentRequestDto,
            HttpServletRequest httpRequest, HttpServletResponse httpResponse) {

        // check user
        String userId = (String)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer leftCount = null;

        if(userId == null){
            String commentInputToken = CookieUtil.getTokenFromCookies(CommentController.commentToken, httpRequest);

            if(commentInputToken == null || commentInputToken.isEmpty()) {
                // 횟수 부족, 나중에 api명세서 만들면 추후 수정
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            userId = guestId;
            leftCount = Integer.valueOf(jwtUtil.extractToken(commentInputToken, "count").get("count"));
        }

        commentRequestDto.setUserId(Long.valueOf(userId));
        commentRequestDto.setQuestionId(questionId);

        // add comment
        commentService.addComment(commentRequestDto);

        // update comment token
        if(leftCount != null) {
            String commentOutputToken = jwtUtil.generateCommentToken(leftCount-1);
            CookieUtil.addCookie(httpResponse, commentToken, commentOutputToken,
                commentExpirationTime);
        }

        // response
        return ResponseEntity.status(HttpStatus.OK).body("comment added");
    }

    @PutMapping("/pudate/${commentId}")
    public ResponseEntity<?> updateComment(@PathVariable Long commentId, @RequestBody AddCommentRequestDto commentRequestDto) {
        String userId = (String)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        commentRequestDto.setUserId(Long.valueOf(userId));

        commentService.updateComment(commentId, commentRequestDto);

        return ResponseEntity.status(HttpStatus.OK).body("comment updated");
    }

    @DeleteMapping("/delete/${commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.status(HttpStatus.OK).body("comment deleted");
    }
}
