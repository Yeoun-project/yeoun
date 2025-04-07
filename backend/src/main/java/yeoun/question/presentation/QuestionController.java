package yeoun.question.presentation;

import jakarta.servlet.http.HttpServletRequest;
import yeoun.comment.dto.request.SaveCommentRequest;
import yeoun.common.SuccessResponse;
import yeoun.question.domain.QuestionHistoryEntity;
import yeoun.question.dto.request.AddQuestionRequest;
import yeoun.comment.dto.response.CommentResponse;
import yeoun.question.dto.response.CategoryResponseDto;
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

    @PostMapping("/api/question")
    public ResponseEntity<?> addQuestion(@RequestBody @Valid AddQuestionRequest addQuestionRequest) {
        addQuestionRequest.setUserId(JwtService.getUserIdFromAuthentication());
        questionService.addNewQuestion(addQuestionRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResponse("Add question success", null));
    }

//    @PutMapping("/api/question/{questionId}")
//    public ResponseEntity<?> updateQuestion(@RequestBody @Valid AddQuestionRequest addQuestionRequest, @PathVariable("questionId") Long questionId) {
//        addQuestionRequest.setUserId(JwtService.getUserIdFromAuthentication());
//        addQuestionRequest.setId(questionId);
//        questionService.updateQuestion(addQuestionRequest);
//        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse("Edit question success", null));
//    }

//    @DeleteMapping("/api/question/{questionId}")
//    public ResponseEntity<?> deleteQuestion(@PathVariable("questionId") Long questionId) {
//        questionService.deleteQuestion(questionId,JwtService.getUserIdFromAuthentication());
//        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse("Deleted question success", null));
//    }

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


        QuestionDetailResponse questionDetail =
                QuestionDetailResponse.builder()
                        .id(question.getId())
                        .content(question.getContent())
                        .heart(question.getHeart())
                        .commentCount(question.getComments().size())
                        .categoryName(question.getCategory().getName())
                        .createTime(question.getCreatedDateTime())
                        .isAuthor(userId == question.getUser().getId())
                        .build();

        List<CommentResponse> commentDtoList = new ArrayList<>();
        question.getComments().forEach(comment -> {
            if(comment.getUser().getId() == userId){
                questionDetail.setMyComment(CommentResponse.builder()
                    .id(comment.getId())
                    .content(comment.getContent())
                    .createTime(comment.getCreatedDateTime())
                    .build()
                );
            }
            else{
                commentDtoList.add(CommentResponse.builder()
                    .id(comment.getId())
                    .content(comment.getContent())
                    .createTime(comment.getCreatedDateTime())
                    .build()
                );
            }
        });

        questionDetail.setComments(commentDtoList);

        Map<String, Object> response = Map.of("question", questionDetail);

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

    @GetMapping("api/category/{categoryId}")
    public ResponseEntity<?> getQuestionCategories(@PathVariable("categoryId")Long categoryId) {
        List<QuestionEntity> questions = questionService.getAllQuestionsByCategory(categoryId);

        List<QuestionResponse> questionResponseList = questions.stream()
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

}
