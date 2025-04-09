package yeoun.question.presentation;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import yeoun.common.SuccessResponse;
import yeoun.question.dto.request.AddQuestionRequest;
import yeoun.question.dto.response.QuestionDetailResponse;
import yeoun.question.dto.response.QuestionResponse;
import yeoun.question.domain.QuestionEntity;
import yeoun.auth.service.JwtService;
import yeoun.question.service.QuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

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
        questionService.deleteQuestion(questionId, JwtService.getUserIdFromAuthentication());
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse("Deleted question success", null));
    }

    @GetMapping("/api/question/all")
    public ResponseEntity<?> getAllQuestion(
            @RequestParam(required = false) String category,
            @PageableDefault() final Pageable pageable
    ) {
        Slice<QuestionEntity> allQuestions = questionService.getAllQuestions(category, pageable);
        List<QuestionResponse> questionResponseList = allQuestions.stream()
                .map(question -> QuestionResponse.builder()
                        .id(question.getId())
                        .content(question.getContent())
                        .categoryName(question.getCategory().getName())
                        .commentCount(question.getComments().size())
                        .createTime(question.getCreatedDateTime())
                        .build())
                .toList();

        Map<String, Object> response = Map.of(
                "questions", questionResponseList,
                "hasNext", allQuestions.hasNext()
        );
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse("get all questions success", response));
    }

    @GetMapping("/api/question/{questionId}")
    public ResponseEntity<?> getQuestionDetails(@PathVariable("questionId") Long questionId) {
        Long userId = JwtService.getUserIdFromAuthentication();
        QuestionEntity question = questionService.getQuestionWithCommentById(questionId);

        Map<String, Object> response = Map.of("question",
                QuestionDetailResponse.builder()
                        .id(question.getId())
                        .content(question.getContent())
                        .commentCount(question.getComments().size())
                        .categoryName(question.getCategory().getName())
                        .createTime(question.getCreatedDateTime())
                        .isAuthor(userId == question.getUser().getId())
                        .build());

        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse("get question detail success", response));
    }

    @GetMapping("/api/question/my")
    public ResponseEntity<?> getMyQuestion() {
        List<QuestionEntity> myQuestions = questionService.getQuestionsByUserId(JwtService.getUserIdFromAuthentication());

        List<QuestionResponse> questionResponseList = myQuestions.stream()
                .map(question -> QuestionResponse.builder()
                        .id(question.getId())
                        .content(question.getContent())
                        .categoryName(question.getCategory().getName())
                        .commentCount(question.getComments().size())
                        .createTime(question.getCreatedDateTime())
                        .build())
                .toList();

        Map<String, Object> response = Map.of("questions", questionResponseList);
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse("get my questions success", response));
    }

    @GetMapping("/api/question/commented-by-me")
    public ResponseEntity<?> getCommentedQuestions(
            @RequestParam(required = false) String category,
            @PageableDefault(sort = "createdDateTime", direction = Sort.Direction.DESC) final Pageable pageable
    ) {
        Slice<QuestionEntity> questionSlice = questionService.getQuestionUserAnswered(JwtService.getUserIdFromAuthentication(), category, pageable);

        List<QuestionResponse> questionResponses = questionSlice.stream().map(question -> QuestionResponse.builder()
                .id(question.getId())
                .content(question.getContent())
                .commentCount(question.getComments().size())
                .categoryName(question.getCategory().getName())
                .createTime(question.getCreatedDateTime())
                .build()).toList();

        Map<String, Object> response = Map.of(
                "questions", questionResponses,
                "hasNext", questionSlice.hasNext()
        );
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse("get questions success", response));
    }

    @GetMapping("/api/category")
    public ResponseEntity<?> getCategories() {
        Map<String, Object> response = Map.of("categories", questionService.getAllCategories());
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse("get all categories success", response));
    }

}
