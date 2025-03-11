package com.example.demo.service;

import com.example.demo.dto.request.AddQuestionRequestDto;
import com.example.demo.entity.CategoryEntity;
import com.example.demo.entity.QuestionEntity;
import com.example.demo.entity.UserEntity;
import com.example.demo.exception.CustomException;
import com.example.demo.exception.ErrorCode;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.QuestionRepository;
import com.example.demo.repository.UserRepository;
import jakarta.transaction.Transactional;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final QuestionRepository questionRepository;

    @Transactional
    public void addNewQuestion(AddQuestionRequestDto dto) {
        UserEntity user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_PARAMETER, "Invalid user ID"));

        CategoryEntity category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_PARAMETER, "Invalid category ID"));

        questionRepository.save(QuestionEntity.builder()
                .content(dto.getContent())
                .user(user)
                .category(category)
                .heart(0)
                .build()
        );
    }

    public List<QuestionEntity> getAllQuestions() {
        return questionRepository.findAllOrderByCommentsCountDesc();
    }

    public QuestionEntity getQuestionWithCommentById(Long id) {
        return questionRepository.findQuestionWithCommentById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_PARAMETER, "Invalid question ID"));
    }
}
