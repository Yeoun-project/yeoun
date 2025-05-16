package yeoun.question.presentation;

import static yeoun.auth.service.JwtService.getUserIdFromAuthentication;

import yeoun.common.SuccessResponse;
import yeoun.question.dto.request.AddTodayQuestionCommentRequest;
import yeoun.question.dto.response.TodayQuestionDetailResponse;
import yeoun.question.dto.response.TodayQuestionListResponse;
import yeoun.question.dto.response.TodayQuestionResponse;
import yeoun.question.service.TodayQuestionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class TodayQuestionController {

    private final TodayQuestionService todayQuestionService;

    @GetMapping("/public/today-question")
    public ResponseEntity<?> getTodayQuestionGuest() {
        Long userId = getUserIdFromAuthentication();
        TodayQuestionResponse todayQuestionResponse = todayQuestionService.getTodayQuestionGuest(userId);
        return ResponseEntity.status(HttpStatus.OK).body(
                new SuccessResponse("비회원 오늘의 질문 조회를 성공했습니다.", todayQuestionResponse));
    }

    @GetMapping("/api/today-question")
    public ResponseEntity<?> getTodayQuestionMember() {
        Long userId = getUserIdFromAuthentication();
        TodayQuestionResponse todayQuestionResponse = todayQuestionService.getTodayQuestionMember(userId);
        return ResponseEntity.status(HttpStatus.OK).body(
                new SuccessResponse("회원 오늘의 질문 조회를 성공했습니다.", todayQuestionResponse));
    }

    @PostMapping("/public/today-question/comment")
    public ResponseEntity<?> addTodayQuestionComment(
            @RequestBody @Valid AddTodayQuestionCommentRequest request
    ) {
        Long userId = getUserIdFromAuthentication();
        todayQuestionService.addTodayQuestionComment(userId, request);
        return ResponseEntity.status(HttpStatus.OK).body(
                new SuccessResponse("오늘의 질문 답변 추가를 성공했습니다.", null));
    }

    @PatchMapping("/public/today-question/comment")
    public ResponseEntity<?> updateTodayQuestionComment(
            @RequestBody @Valid AddTodayQuestionCommentRequest request
    ) {
        Long userId = getUserIdFromAuthentication();
        todayQuestionService.updateTodayQuestionComment(userId, request);
        return ResponseEntity.status(HttpStatus.OK).body(
                new SuccessResponse("오늘의 질문 답변 수정을 성공했습니다.", null));
    }

    @GetMapping("/public/today-question/commented-by-me")
    public ResponseEntity<?> getAllCommentedMyTodayQuestions() {
        Long userId = getUserIdFromAuthentication();
        TodayQuestionListResponse todayQuestionListResponse = todayQuestionService.getAllCommentedMyTodayQuestions(userId);
        return ResponseEntity.status(HttpStatus.OK).body(
                new SuccessResponse("내가 답변한 오늘의 질문 리스트 조회를 성공했습니다.", todayQuestionListResponse)
        );
    }

    @GetMapping("/public/today-question/{questionId}")
    public ResponseEntity<SuccessResponse> getTodayQuestionDetail(@PathVariable Long questionId) {
        Long userId = getUserIdFromAuthentication();
        TodayQuestionDetailResponse todayQuestionDetailResponse = todayQuestionService.getTodayQuestionDetail(questionId, userId);
        return ResponseEntity.ok(new SuccessResponse("오늘의 질문 상세 조회를 성공했습니다.", todayQuestionDetailResponse));
    }

}
