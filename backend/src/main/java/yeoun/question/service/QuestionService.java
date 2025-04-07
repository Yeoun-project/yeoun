package yeoun.question.service;

import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import yeoun.question.domain.repository.CategoryResponseDao;
import yeoun.question.dto.request.AddQuestionRequest;
import yeoun.question.domain.CategoryEntity;
import yeoun.question.domain.QuestionEntity;
import yeoun.question.dto.response.CategoryResponseDto;
import yeoun.user.domain.UserEntity;
import yeoun.exception.CustomException;
import yeoun.exception.ErrorCode;
import yeoun.question.domain.repository.CategoryRepository;
import yeoun.question.domain.repository.QuestionRepository;
import yeoun.user.domain.repository.UserRepository;
import jakarta.transaction.Transactional;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class QuestionService {

    private final ForbiddenWordService forbiddenWordService;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final QuestionRepository questionRepository;

    @Transactional
    public void addNewQuestion(AddQuestionRequest dto) throws CustomException {
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

    @Transactional
    public void updateQuestion(AddQuestionRequest dto) {
        Optional<QuestionEntity> questionOptional = questionRepository.findQuestionById(dto.getId());

        if(questionOptional.isEmpty())
            throw new CustomException(ErrorCode.INVALID_PARAMETER, "Invalid question ID");

        QuestionEntity question = questionOptional.get();

        if(question.getUser().getId() != dto.getUserId())
            throw new CustomException(ErrorCode.INVALID_PARAMETER, "Invalid user ID");

        CategoryEntity category = categoryRepository.findById(dto.getCategoryId())
            .orElseThrow(() -> new CustomException(ErrorCode.INVALID_PARAMETER, "Invalid category ID"));

        forbiddenWordService.validateForbiddenWord(dto.getContent());

        questionRepository.save(QuestionEntity.builder()
                .id(question.getId())
                .content(dto.getContent())
                .user(question.getUser())
                .category(category)
                .heart(question.getHeart())
                .build());
    }

    public void deleteQuestion(long id, Long userId) {
        Optional<QuestionEntity> questionOptional = questionRepository.findQuestionById(id);
        if(questionOptional.isEmpty())
            throw new CustomException(ErrorCode.INVALID_PARAMETER, "Invalid question ID");

        QuestionEntity question = questionOptional.get();

        if(question.getUser().getId() != userId)
            throw new CustomException(ErrorCode.INVALID_PARAMETER, "Invalid user ID");

        questionRepository.delete(question);
    }

    public List<QuestionEntity> getAllQuestions() {
        return questionRepository.findAllOrderByCommentsCountDesc();
    }

    public QuestionEntity getQuestionWithCommentById(Long id) {
        return questionRepository.findQuestionWithCommentById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_PARAMETER, "Invalid question ID"));
    }

    public List<QuestionEntity> getQuestionsByUserId(long userId) {
        return questionRepository.findByUserId(userId);
    }

    public List<CategoryResponseDto> getAllCategories() {
        //List<CategoryEntity> categories = categoryRepository.findAll();
        List<CategoryResponseDao> daoList = questionRepository.findCategoriesWithCount();
        return daoList
            .stream()
            .map(dao-> {
                return CategoryResponseDto
                    .builder()
                    .id(dao.getCategory().getId())
                    .name(dao.getCategory().getName())
                    .questionCount(dao.getCount())
                    .build();
            })
            .toList();
    }

    public List<QuestionEntity> getAllQuestionsByCategory(Long categoryId) {
        return questionRepository.findAllByCategoryId(categoryId);
    }
}
