package yeoun.question.presentation;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import yeoun.common.SuccessResponse;
import yeoun.question.domain.Question;
import yeoun.question.dto.request.AddQuestionRequest;
import yeoun.question.dto.response.QuestionDetailResponse;
import yeoun.question.dto.response.QuestionListResponse;
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
        return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResponse("질문 추가를 성공했습니다.", null));
    }

    // 기능 없어짐 -> 주석 처리
//    @PutMapping("/api/question/{questionId}")
//    public ResponseEntity<?> updateQuestion(
//            @RequestBody @Valid AddQuestionRequest addQuestionRequest,
//            @PathVariable("questionId") Long questionId
//    ) {
//        addQuestionRequest.setUserId(JwtService.getUserIdFromAuthentication());
//        addQuestionRequest.setId(questionId);
//        questionService.updateQuestion(addQuestionRequest);
//        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse("Edit question success", null));
//    }
//
//    @DeleteMapping("/api/question/{questionId}")
//    public ResponseEntity<?> deleteQuestion(@PathVariable("questionId") Long questionId) {
//        questionService.deleteQuestion(questionId, JwtService.getUserIdFromAuthentication());
//        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse("Deleted question success", null));
//    }

    @GetMapping("/api/question/all")
    public ResponseEntity<?> getAllQuestion(
            @RequestParam(required = false) String category,
            @PageableDefault() final Pageable pageable
    ) {
        QuestionListResponse questionListResponse = questionService.getAllQuestions(category, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse("질문 목록 조회를 성공했습니다.", questionListResponse));
    }

    @GetMapping("/api/question/{questionId}")
    public ResponseEntity<?> getQuestionDetails(@PathVariable("questionId") Long questionId) {
        Long userId = JwtService.getUserIdFromAuthentication();

        QuestionDetailResponse questionDetailResponse = questionService.getQuestionDetail(userId, questionId);
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse("질문 상세 조회를 성공했습니다.", questionDetailResponse));
    }

    @GetMapping("/api/question/my")
    public ResponseEntity<?> getMyQuestion(@PageableDefault() final Pageable pageable) {
        Long userId = JwtService.getUserIdFromAuthentication();
        QuestionListResponse questionListResponse = questionService.getMyQuestions(userId, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(
                new SuccessResponse("내가 작성한 질문 목록 조회를 성공했습니다.", questionListResponse));
    }

    @GetMapping("/api/question/commented-by-me")
    public ResponseEntity<?> getCommentedQuestions(
            @RequestParam(required = false) String category,
            @PageableDefault(sort = "createTime", direction = Sort.Direction.DESC) final Pageable pageable
    ) {
        QuestionListResponse questionListResponse = questionService.getQuestionUserAnswered(JwtService.getUserIdFromAuthentication(), category, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(
                new SuccessResponse("내가 답변한 질문 목록 조회를 성공했습니다.", questionListResponse));
    }

    @GetMapping("/api/category")
    public ResponseEntity<?> getCategories() {
        Map<String, Object> response = Map.of("categories", questionService.getAllCategories());
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse("카테고리 목록 조회를 성공했습니다.", response));
    }

}
