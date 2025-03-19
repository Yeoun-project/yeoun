package com.example.demo.service;

import com.example.demo.dto.request.AddQuestionRequestDto;
import com.example.demo.entity.CategoryEntity;
import com.example.demo.entity.QuestionEntity;
import com.example.demo.entity.QuestionHistoryEntity;
import com.example.demo.entity.UserEntity;
import com.example.demo.exception.CustomException;
import com.example.demo.exception.ErrorCode;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.QuestionHistoryRepository;
import com.example.demo.repository.QuestionRepository;
import com.example.demo.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final EntityManager entityManager;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final QuestionRepository questionRepository;
    private final QuestionHistoryRepository questionHistoryRepository;

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

    @Transactional
    public QuestionEntity getTodayQuestionGuest(Long userId) {
        userRepository.findById(userId).orElseThrow(() -> new CustomException(ErrorCode.INVALID_PARAMETER, "유저 정보가 잘못 되었습니다"));
        Optional<QuestionEntity> todayQuestion = questionRepository.findTodayQuestion(userId);
        // 오늘 조회했던 오늘의 질문이 있으면 그대로 반환
        if (todayQuestion.isPresent()) return todayQuestion.get();
        Optional<QuestionEntity> randomPopularityQuestion = questionRepository.findRandomPopularityQuestionExcludingHistory(userId);
        if (randomPopularityQuestion.isPresent()) {
            questionHistoryRepository.save(
                    QuestionHistoryEntity.builder()
                            .user(entityManager.getReference(UserEntity.class, userId))
                            .question(randomPopularityQuestion.get())
                            .build()
            );

            return randomPopularityQuestion.get();
        }
        // 댓글이 10개 이상인 인기 질문이 없으면 고정 질문 중 1개 반환
        return findRandomFixedQuestionExcludingHistory(userId);
    }

    @Transactional
    public QuestionEntity getTodayQuestionMember(Long userId) {
        Optional<QuestionEntity> todayQuestion = questionRepository.findTodayQuestion(userId);
        return todayQuestion.orElseGet(() -> findRandomFixedQuestionExcludingHistory(userId));
    }

    private QuestionEntity findRandomFixedQuestionExcludingHistory(Long userId) {
        QuestionEntity newTodayQuestion = questionRepository.findRandomFixedQuestionExcludingHistory(userId)
                // 오늘의 질문을 한개도 못 가져오는 경우는 is_fixed 질문이 한개도 없는 경우 밖에 없음
                .orElseThrow(() -> new CustomException(ErrorCode.INTERNAL_SERVER_ERROR, "오늘의 질문을 가져오는데 실패했습니다."));
        System.out.println(userId);
        questionHistoryRepository.save(
                QuestionHistoryEntity.builder()
                        .user(entityManager.getReference(UserEntity.class, userId))
                        .question(entityManager.getReference(QuestionEntity.class, newTodayQuestion.getId()))
                        .build()
        );

        return newTodayQuestion;
    }
}
