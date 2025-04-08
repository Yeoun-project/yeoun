package yeoun.question.presentation;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import yeoun.auth.infrastructure.SecurityUtil;
import yeoun.auth.service.JwtService;
import yeoun.comment.dto.request.SaveCommentRequest;
import yeoun.common.SuccessResponse;
import yeoun.question.domain.QuestionHistoryEntity;
import yeoun.question.dto.response.TodayQuestionDetailResponse;
import yeoun.question.dto.response.TodayQuestionListResponse;
import yeoun.question.dto.response.TodayQuestionResponse;
import yeoun.question.service.TodayQuestionService;

@RestController
@RequiredArgsConstructor
public class TodayQuestionController {

    private final TodayQuestionService todayQuestionService;

    @GetMapping("/public/question/today")
    public ResponseEntity<?> getTodayQuestionGuest(
        HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (SecurityUtil.isLoggedIn()) response.sendRedirect("/api/question/today");

        Long userId = JwtService.getUserIdFromAuthentication();

        QuestionHistoryEntity todayQuestion = todayQuestionService.getTodayQuestionGuest(userId);
        return ResponseEntity.status(HttpStatus.OK).body(
            new SuccessResponse("Get today's question for guest success", TodayQuestionResponse.builder()
                .id(todayQuestion.getId())
                .created_at(todayQuestion.getCreatedDateTime())
                .content(todayQuestion.getQuestion().getContent())
                .isComment(todayQuestion.getComment() != null)
                .build())
        );
    }

    @GetMapping("/api/question/today")
    public ResponseEntity<?> getTodayQuestionMember() {
        Long userId = JwtService.getUserIdFromAuthentication();
        QuestionHistoryEntity todayQuestion = todayQuestionService.getTodayQuestionMember(userId);

        return ResponseEntity.status(HttpStatus.OK).body(
            new SuccessResponse("Get today's question for member success", TodayQuestionResponse.builder()
                .id(todayQuestion.getId())
                .created_at(todayQuestion.getCreatedDateTime())
                .content(todayQuestion.getQuestion().getContent())
                .isComment(todayQuestion.getComment() != null)
                .build())
        );
    }

    @PostMapping("/public/question/today/{questionHistoryId}")
    public ResponseEntity<?> commentTodayQuestion(@RequestBody @Valid SaveCommentRequest dto, @PathVariable("questionHistoryId") Long questionHistoryId) {
        dto.setUserId(JwtService.getUserIdFromAuthentication());
        dto.setId(questionHistoryId);

        todayQuestionService.commentQuestion(dto);

        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse("Comment success"));
    }

    @GetMapping("/public/question/today/my")
    public ResponseEntity<?> getMyTodayQuestions() {
        List<QuestionHistoryEntity> questionHistories = todayQuestionService.getAllMyTodayQuestions(JwtService.getUserIdFromAuthentication());
        Map<String, Object> response = Map.of("questions",
            questionHistories.stream().map( qh->
                TodayQuestionListResponse
                    .builder()
                    .id(qh.getId())
                    .created_at(qh.getCreatedDateTime())
                    .content(qh.getQuestion().getContent())
                    .build()
            ).toList()
        );

        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse("success get my today questions" ,response));
    }

    @GetMapping("/public/question/today/my/{questionHistoryId}")
    public ResponseEntity<?> getMyTodayQuestion(@PathVariable("questionHistoryId") Long questionHistoryId) {
        QuestionHistoryEntity questionHistory = todayQuestionService.getMyTodayQuestion(JwtService.getUserIdFromAuthentication(), questionHistoryId);

        TodayQuestionDetailResponse response =
            TodayQuestionDetailResponse
                .builder()
                .id(questionHistory.getId())
                .created_at(questionHistory.getCreatedDateTime())
                .comment(questionHistory.getComment())
                .content(questionHistory.getQuestion().getContent())
                .build();


        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse("success get my today question" ,response));
    }
}
