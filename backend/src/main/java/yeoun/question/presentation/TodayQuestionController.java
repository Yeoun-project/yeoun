package yeoun.question.presentation;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import yeoun.auth.service.JwtService;
import yeoun.common.SuccessResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import yeoun.comment.dto.request.SaveCommentRequest;
import yeoun.question.domain.QuestionHistory;
import yeoun.question.dto.request.AddTodayQuestionCommentRequest;
import yeoun.question.dto.response.TodayQuestionDetailResponse;
import yeoun.question.dto.response.TodayQuestionListResponse;
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
                new SuccessResponse("Get today's question for guest success", todayQuestionResponse));
    }

    @GetMapping("/api/today-question")
    public ResponseEntity<?> getTodayQuestionMember() {
        Long userId = JwtService.getUserIdFromAuthentication();
        TodayQuestionResponse todayQuestionResponse = todayQuestionService.getTodayQuestionMember(userId);
        return ResponseEntity.status(HttpStatus.OK).body(
                new SuccessResponse("Get today's question for member success", todayQuestionResponse));
    }

    @PostMapping("/public/today-question/{questionHistoryId}")
    public ResponseEntity<?> commentTodayQuestion(@RequestBody @Valid SaveCommentRequest dto, @PathVariable("questionHistoryId") Long questionHistoryId) {
        dto.setUserId(JwtService.getUserIdFromAuthentication());
        dto.setId(questionHistoryId);

        todayQuestionService.commentQuestion(dto);

        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse("Comment success"));
    }

    @GetMapping("/public/today-question/my")
    public ResponseEntity<?> getMyTodayQuestions() {
        List<QuestionHistory> questionHistories = todayQuestionService.getAllMyTodayQuestions(JwtService.getUserIdFromAuthentication());
        Map<String, Object> response = Map.of("questions",
            questionHistories.stream().map( qh->
                TodayQuestionListResponse
                    .builder()
                    .id(qh.getId())
                    .created_at(qh.getCreateTime())
                    .content(qh.getQuestion().getContent())
                    .build()
            ).toList()
        );

        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse("success get my today questions" ,response));
    }

    @GetMapping("/public/today-question/my/{questionHistoryId}")
    public ResponseEntity<?> getMyTodayQuestion(@PathVariable("questionHistoryId") Long questionHistoryId) {
        QuestionHistory questionHistory = todayQuestionService.getMyTodayQuestion(JwtService.getUserIdFromAuthentication(), questionHistoryId);

        TodayQuestionDetailResponse response =
            TodayQuestionDetailResponse
                .builder()
                .id(questionHistory.getId())
                .created_at(questionHistory.getCreateTime())
                .comment(questionHistory.getComment())
                .content(questionHistory.getQuestion().getContent())
                .build();


        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse("success get my today question" ,response));
    }

    @PostMapping("/public/today-question/comment")
    public ResponseEntity<?> addTodayQuestionComment(
            @RequestBody @Valid AddTodayQuestionCommentRequest request
    ) {
        Long userId = JwtService.getUserIdFromAuthentication();
        todayQuestionService.addTodayQuestionComment(userId, request);
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse("Add today question comment success", null));
    }

}
