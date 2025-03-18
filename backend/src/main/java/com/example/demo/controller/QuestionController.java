package com.example.demo.controller;

import com.example.demo.common.ErrorResponse;
import com.example.demo.common.SuccessResponse;
import com.example.demo.dto.request.AddQuestionRequestDto;
import com.example.demo.dto.response.CommentResponseDto;
import com.example.demo.dto.response.QuestionDetailResponseDto;
import com.example.demo.dto.response.QuestionResponseDto;
import com.example.demo.dto.response.TodayQuestionResponseDto;
import com.example.demo.entity.QuestionEntity;
import com.example.demo.jwt.JwtUtil;
import com.example.demo.service.QuestionService;
import com.example.demo.util.SecurityUtil;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
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

    @GetMapping("/public/question/today")
    public ResponseEntity<?> getTodayQuestionGuest(HttpServletResponse response) throws IOException {
        if (SecurityUtil.isLoggedIn()) response.sendRedirect("/api/question/today");

        // IP로 가입된 비회원 데이터가 있는지 찾기
        // 없으면 비회원으로 가입

        Long userId = 4L; // 비회원의 User PK (임시)
        QuestionEntity todayQuestion = questionService.getTodayQuestionGuest(userId);
        return ResponseEntity.status(HttpStatus.OK).body(
                new SuccessResponse("Get today's question for guest success", TodayQuestionResponseDto.builder()
                        .id(todayQuestion.getId())
                        .content(todayQuestion.getContent())
                        .build())
        );
    }

    @GetMapping("/api/question/today")
    public ResponseEntity<?> getTodayQuestionMember() {
        Long userId = JwtUtil.getUserIdFromAuthentication();
        QuestionEntity todayQuestion = questionService.getTodayQuestionMember(userId);

        return ResponseEntity.status(HttpStatus.OK).body(
                new SuccessResponse("Get today's question for member success", TodayQuestionResponseDto.builder()
                        .id(todayQuestion.getId())
                        .content(todayQuestion.getContent())
                        .build())
        );
    }

}
