package yeoun.question.presentation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import yeoun.auth.service.JwtService;
import yeoun.common.SuccessResponse;
import yeoun.question.dto.request.AddTodayQuestionCommentRequest;
import yeoun.question.dto.response.TodayQuestionResponse;
import yeoun.question.service.TodayQuestionService;

@RestController
@RequiredArgsConstructor
public class TodayQuestionController {

    private final TodayQuestionService todayQuestionService;

    @GetMapping("/public/today-question")
    public ResponseEntity<?> getTodayQuestionGuest() {
        Long userId = JwtService.getUserIdFromAuthentication();
        TodayQuestionResponse todayQuestionResponse = todayQuestionService.getTodayQuestionGuest(userId);
        return ResponseEntity.status(HttpStatus.OK).body(
                new SuccessResponse("비회원 오늘의 질문 조회를 성공했습니다.", todayQuestionResponse));
    }

    @GetMapping("/api/today-question")
    public ResponseEntity<?> getTodayQuestionMember() {
        Long userId = JwtService.getUserIdFromAuthentication();
        TodayQuestionResponse todayQuestionResponse = todayQuestionService.getTodayQuestionMember(userId);
        return ResponseEntity.status(HttpStatus.OK).body(
                new SuccessResponse("회원 오늘의 질문 조회를 성공했습니다.", todayQuestionResponse));
    }

    @PostMapping("/public/today-question/comment")
    public ResponseEntity<?> addTodayQuestionComment(
            @RequestBody @Valid AddTodayQuestionCommentRequest request
    ) {
        Long userId = JwtService.getUserIdFromAuthentication();
        todayQuestionService.addTodayQuestionComment(userId, request);
        return ResponseEntity.status(HttpStatus.OK).body(
                new SuccessResponse("오늘의 질문 답변 추가를 성공했습니다.", null));
    }

    @PatchMapping("/public/today-question/comment")
    public ResponseEntity<?> updateTodayQuestionComment(
            @RequestBody @Valid AddTodayQuestionCommentRequest request
    ) {
        Long userId = JwtService.getUserIdFromAuthentication();
        todayQuestionService.updateTodayQuestionComment(userId, request);
        return ResponseEntity.status(HttpStatus.OK).body(
                new SuccessResponse("오늘의 질문 답변 수정을 성공했습니다.", null));
    }

}
