package com.example.demo.controller;

import com.example.demo.common.ErrorResponse;
import com.example.demo.common.SuccessResponse;
import com.example.demo.dto.request.AddQuestionRequestDto;
import com.example.demo.dto.response.CommentResponseDto;
import com.example.demo.dto.response.QuestionDetailResponseDto;
import com.example.demo.dto.response.QuestionResponseDto;
import com.example.demo.entity.QuestionEntity;
import com.example.demo.jwt.JwtUtil;
import com.example.demo.service.QuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    @PostMapping("/api/question")
    public ResponseEntity<?> addQuestion(@RequestBody @Valid AddQuestionRequestDto addQuestionRequestDto) {
        addQuestionRequestDto.setUserId(JwtUtil.getUserIdFromAuthentication());
        questionService.addNewQuestion(addQuestionRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResponse("Add question success", null));
    }

    @GetMapping("/api/question/all")
    public ResponseEntity<?> getAllQuestion() {
        List<QuestionEntity> allQuestions = questionService.getAllQuestions();
        List<QuestionResponseDto> questionResponseDtoList = new ArrayList<>();
        for (QuestionEntity question : allQuestions) {
            questionResponseDtoList.add(QuestionResponseDto.builder()
                    .id(question.getId())
                    .content(question.getContent())
                    .heart(question.getHeart())
                    .categoryName(question.getCategory().getName())
                    .commentCount(question.getComments().size())
                    .createTime(question.getCreatedDateTime())
                    .build()
            );
        }

        Map<String, Object> response = Map.of("questions", questionResponseDtoList);
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse("get all questions success", response));
    }

    @GetMapping("/api/question/{questionId}")
    public ResponseEntity<?> getQuestionDetails(@PathVariable("questionId") Long questionId) {
        QuestionEntity question = questionService.getQuestionWithCommentById(questionId);

        List<CommentResponseDto> commentDtoList = question.getComments().stream()
                .map(comment -> CommentResponseDto.builder()
                        .id(comment.getId())
                        .content(comment.getContent())
                        .createTime(comment.getCreatedDateTime())
                        .build())
                .toList();

        return ResponseEntity.status(HttpStatus.OK).body(
                QuestionDetailResponseDto.builder()
                        .id(question.getId())
                        .content(question.getContent())
                        .heart(question.getHeart())
                        .categoryName(question.getCategory().getName())
                        .createTime(question.getCreatedDateTime())
                        .comments(commentDtoList)
                        .build()
        );
    }

}
