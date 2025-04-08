package yeoun.question.presentation;

import yeoun.comment.service.CommentLikeService;
import yeoun.comment.service.CommentService;
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
        QuestionDetailResponse questionDetail = questionService.getQuestionDetail(questionId, JwtService.getUserIdFromAuthentication());
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse("get question detail success", questionDetail));
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

}
