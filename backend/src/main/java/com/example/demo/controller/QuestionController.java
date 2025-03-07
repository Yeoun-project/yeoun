package com.example.demo.controller;

import com.example.demo.common.ErrorResponse;
import com.example.demo.common.SuccessResponse;
import com.example.demo.dto.request.AddQuestionRequestDto;
import com.example.demo.dto.response.QuestionResponseDto;
import com.example.demo.entity.QuestionEntity;
import com.example.demo.jwt.JwtUtil;
import com.example.demo.service.QuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/question")
public class QuestionController {

    private final QuestionService questionService;

    @PostMapping
    public ResponseEntity<?> addQuestion(@RequestBody @Valid AddQuestionRequestDto addQuestionRequestDto) {
        try {
            addQuestionRequestDto.setUserId(JwtUtil.getUserIdFromAuthentication());
            questionService.addNewQuestion(addQuestionRequestDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResponse("Add question success", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse("", e.getLocalizedMessage()));
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllQuestion() {
        List<QuestionEntity> allQuestions = questionService.getAllQuestions();
        List<QuestionResponseDto> questionResponseDtoList = allQuestions.stream()
                .map(question -> QuestionResponseDto.builder()
                    .id(question.getId())
                    .content(question.getContent())
                    .heart(question.getHeart())
                    .categoryName(question.getCategory().getName())
                    .commentCount(question.getComments().size())
                    .createTime(question.getCreatedDateTime())
                    .build())
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse("get all questions success", questionResponseDtoList));
    }

}
