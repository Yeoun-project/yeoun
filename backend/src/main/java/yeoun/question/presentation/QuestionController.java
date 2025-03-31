package yeoun.question.presentation;

import jakarta.websocket.server.PathParam;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import yeoun.common.SuccessResponse;
import yeoun.question.domain.CategoryEntity;
import yeoun.question.domain.repository.CategoryRepository;
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
import yeoun.user.service.UserService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;
    private final TodayQuestionService todayQuestionService;
    private final UserService userService;

    @PostMapping("/api/question")
    public ResponseEntity<?> addQuestion(@RequestBody @Valid AddQuestionRequest addQuestionRequest) {
        addQuestionRequest.setUserId(JwtService.getUserIdFromAuthentication());
        questionService.addNewQuestion(addQuestionRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResponse("Add question success", null));
    }

    @PutMapping("/api/question/{questionId}")
    public ResponseEntity<?> updateQuestion(@RequestBody @Valid AddQuestionRequest addQuestionRequest, @PathVariable("questionId") Long questionId) {
        addQuestionRequest.setUserId(JwtService.getUserIdFromAuthentication());
        addQuestionRequest.setId(questionId);
        questionService.updateQuestion(addQuestionRequest);
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse("Edit question success", null));
    }

    @DeleteMapping("/api/question/{questionId}")
    public ResponseEntity<?> deleteQuestion(@PathVariable("questionId") Long questionId) {
        questionService.deleteQuestion(questionId,JwtService.getUserIdFromAuthentication());
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse("Deleted question success", null));
    }

    @GetMapping("/api/question/all")
    public ResponseEntity<?> getAllQuestion() {
        List<QuestionEntity> allQuestions = questionService.getAllQuestions();
        List<QuestionResponse> questionResponseList = allQuestions.stream()
                .map(question -> QuestionResponse.builder()
                        .id(question.getId())
                        .content(question.getContent())
                        .heart(question.getHeart())
                        .categoryName(question.getCategory().getName())
                        .commentCount(question.getComments().size())
                        .createTime(question.getCreatedDateTime())
                        .build())
                .toList();

        Map<String, Object> response = Map.of("questions", questionResponseList);
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse("get all questions success", response));
    }

    @GetMapping("/api/question/{questionId}")
    public ResponseEntity<?> getQuestionDetails(@PathVariable("questionId") Long questionId) {
        Long userId = JwtService.getUserIdFromAuthentication();
        QuestionEntity question = questionService.getQuestionWithCommentById(questionId);

        List<CommentResponse> commentDtoList = question.getComments().stream()
                .map(comment -> CommentResponse.builder()
                        .id(comment.getId())
                        .content(comment.getContent())
                        .createTime(comment.getCreatedDateTime())
                        .build())
                .toList();

        Map<String, Object> response = Map.of("question",
                QuestionDetailResponse.builder()
                        .id(question.getId())
                        .content(question.getContent())
                        .heart(question.getHeart())
                        .commentCount(question.getComments().size())
                        .categoryName(question.getCategory().getName())
                        .createTime(question.getCreatedDateTime())
                        .isAuthor(userId == question.getUser().getId())
                        .comments(commentDtoList)
                        .build());

        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse("get question detail success",response));
    }

    @GetMapping("/api/question/my")
    public ResponseEntity<?> getMyQuestion() {
        List<QuestionEntity> myQuestions = questionService.getQuestionsByUserId(JwtService.getUserIdFromAuthentication());

        List<QuestionResponse> questionResponseList = myQuestions.stream()
                .map(question -> QuestionResponse.builder()
                        .id(question.getId())
                        .content(question.getContent())
                        .heart(question.getHeart())
                        .categoryName(question.getCategory().getName())
                        .commentCount(question.getComments().size())
                        .createTime(question.getCreatedDateTime())
                        .build())
                .toList();
        Map<String, Object> response = Map.of("questions", questionResponseList);
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse("get my questions success", response));
    }

    @GetMapping("/api/category")
    public ResponseEntity<?> getCategories() {
        Map<String, Object> response = Map.of("categories", questionService.getAllCategories());
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse("get all categories success", response));
    }

    @GetMapping("/public/question/today")
    public ResponseEntity<?> getTodayQuestionGuest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (SecurityUtil.isLoggedIn()) response.sendRedirect("/api/question/today");

        Long userId = JwtService.getUserIdFromAuthentication();

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
