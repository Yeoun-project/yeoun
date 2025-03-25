package yeoun.question.presentation;

import yeoun.common.SuccessResponse;
import yeoun.question.dto.request.AddQuestionRequest;
import yeoun.comment.dto.response.CommentResponse;
import yeoun.question.dto.response.QuestionDetailResponse;
import yeoun.question.dto.response.QuestionResponse;
import yeoun.question.dto.response.TodayQuestionResponse;
import yeoun.question.domain.QuestionEntity;
import yeoun.auth.service.JwtService;
import yeoun.question.service.QuestionService;
import yeoun.auth.infrastructure.SecurityUtil;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import yeoun.question.service.TodayQuestionService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;
    private final TodayQuestionService todayQuestionService;

    @PostMapping("/api/question")
    public ResponseEntity<?> addQuestion(@RequestBody @Valid AddQuestionRequest addQuestionRequest) {
        addQuestionRequest.setUserId(JwtService.getUserIdFromAuthentication());
        questionService.addNewQuestion(addQuestionRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResponse("Add question success", null));
    }

    @GetMapping("/api/question/all")
    public ResponseEntity<?> getAllQuestion() {
        List<QuestionEntity> allQuestions = questionService.getAllQuestions();
        List<QuestionResponse> questionResponseList = new ArrayList<>();
        for (QuestionEntity question : allQuestions) {
            questionResponseList.add(QuestionResponse.builder()
                    .id(question.getId())
                    .content(question.getContent())
                    .heart(question.getHeart())
                    .categoryName(question.getCategory().getName())
                    .commentCount(question.getComments().size())
                    .createTime(question.getCreatedDateTime())
                    .build()
            );
        }

        Map<String, Object> response = Map.of("questions", questionResponseList);
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse("get all questions success", response));
    }

    @GetMapping("/api/question/{questionId}")
    public ResponseEntity<?> getQuestionDetails(@PathVariable("questionId") Long questionId) {
        QuestionEntity question = questionService.getQuestionWithCommentById(questionId);

        List<CommentResponse> commentDtoList = question.getComments().stream()
                .map(comment -> CommentResponse.builder()
                        .id(comment.getId())
                        .content(comment.getContent())
                        .createTime(comment.getCreatedDateTime())
                        .build())
                .toList();

        return ResponseEntity.status(HttpStatus.OK).body(
                QuestionDetailResponse.builder()
                        .id(question.getId())
                        .content(question.getContent())
                        .heart(question.getHeart())
                        .categoryName(question.getCategory().getName())
                        .createTime(question.getCreatedDateTime())
                        .comments(commentDtoList)
                        .build()
        );
    }

    @GetMapping("/public/question/today")
    public ResponseEntity<?> getTodayQuestionGuest(HttpServletResponse response) throws IOException {
        if (SecurityUtil.isLoggedIn()) response.sendRedirect("/api/question/today");

        // IP로 가입된 비회원 데이터가 있는지 찾기
        // 없으면 비회원으로 가입

        Long userId = 4L; // 비회원의 User PK (임시)
        QuestionEntity todayQuestion = todayQuestionService.getTodayQuestionGuest(userId);
        return ResponseEntity.status(HttpStatus.OK).body(
                new SuccessResponse("Get today's question for guest success", TodayQuestionResponse.builder()
                        .id(todayQuestion.getId())
                        .content(todayQuestion.getContent())
                        .build())
        );
    }

    @GetMapping("/api/question/today")
    public ResponseEntity<?> getTodayQuestionMember() {
        Long userId = JwtService.getUserIdFromAuthentication();
        QuestionEntity todayQuestion = todayQuestionService.getTodayQuestionMember(userId);

        return ResponseEntity.status(HttpStatus.OK).body(
                new SuccessResponse("Get today's question for member success", TodayQuestionResponse.builder()
                        .id(todayQuestion.getId())
                        .content(todayQuestion.getContent())
                        .build())
        );
    }

}
