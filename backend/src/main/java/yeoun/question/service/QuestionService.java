package yeoun.question.service;

import yeoun.question.dto.request.AddQuestionRequest;
import yeoun.question.domain.CategoryEntity;
import yeoun.question.domain.QuestionEntity;
import yeoun.question.domain.QuestionHistoryEntity;
import yeoun.user.domain.UserEntity;
import yeoun.exception.CustomException;
import yeoun.exception.ErrorCode;
import yeoun.question.domain.repository.CategoryRepository;
import yeoun.question.domain.repository.QuestionHistoryRepository;
import yeoun.question.domain.repository.QuestionRepository;
import yeoun.user.domain.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final ForbiddenWordService forbiddenWordService;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final QuestionRepository questionRepository;

    @Transactional
    public void addNewQuestion(AddQuestionRequest dto) {
        UserEntity user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_PARAMETER, "Invalid user ID"));

        CategoryEntity category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_PARAMETER, "Invalid category ID"));

        forbiddenWordService.validateForbiddenWord(dto.getContent());

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
