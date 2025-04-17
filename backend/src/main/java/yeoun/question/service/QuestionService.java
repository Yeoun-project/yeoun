package yeoun.question.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import yeoun.question.dto.request.AddQuestionRequest;
import yeoun.question.domain.Category;
import yeoun.question.domain.Question;
import yeoun.question.dto.response.*;
import yeoun.user.domain.User;
import lombok.extern.slf4j.Slf4j;
import yeoun.question.domain.repository.CategoryResponseDao;
import yeoun.exception.CustomException;
import yeoun.exception.ErrorCode;
import yeoun.question.domain.repository.CategoryRepository;
import yeoun.question.domain.repository.QuestionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import yeoun.user.service.UserService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class QuestionService {

    private final EntityManager entityManager;
    private final UserService userService;
    private final ForbiddenWordService forbiddenWordService;
    private final CategoryRepository categoryRepository;
    private final QuestionRepository questionRepository;

    @Transactional
    public void addNewQuestion(AddQuestionRequest dto) throws CustomException {
        userService.validateUser(dto.getUserId());

        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_PARAMETER, "카테고리 ID가 잘못 되었습니다."));

        forbiddenWordService.validateForbiddenWord(dto.getContent());

        questionRepository.save(Question.builder()
                .content(dto.getContent())
                .user(entityManager.find(User.class, dto.getUserId()))
                .category(category)
                .build()
        );
    }

//    @Transactional
//    public void updateQuestion(AddQuestionRequest dto) {
//        userService.validateUser(dto.getUserId());
//
//        Question question = questionRepository.findQuestionById(dto.getId())
//                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_PARAMETER, "Invalid question ID"));
//
//        if (!Objects.equals(question.getUser().getId(), dto.getUserId()))
//            throw new CustomException(ErrorCode.INVALID_PARAMETER, "Invalid user ID");
//
//        Category category = categoryRepository.findById(dto.getCategoryId())
//                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_PARAMETER, "Invalid category ID"));
//
//        forbiddenWordService.validateForbiddenWord(dto.getContent());
//
//        questionRepository.save(Question.builder()
//                .id(question.getId())
//                .content(dto.getContent())
//                .user(question.getUser())
//                .category(category)
//                .build());
//    }
//
//    @Transactional
//    public void deleteQuestion(Long questionId, Long userId) {
//        Question question = questionRepository.findQuestionById(questionId)
//                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_PARAMETER, "Invalid question ID"));
//
//        if (!Objects.equals(question.getUser().getId(), userId))
//            throw new CustomException(ErrorCode.INVALID_PARAMETER, "Invalid user ID");
//
//        questionRepository.delete(question);
//    }

    @Transactional
    public QuestionListResponse getAllQuestions(String category, Pageable pageable) {
        LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
        LocalDateTime endOfDay = startOfDay.plusDays(1);

        Slice<Question> questionSlice;
        if (category == null || category.isBlank()) {
            questionSlice = questionRepository.findAllOrderByCommentsCount(startOfDay, endOfDay, pageable);
        } else {
            questionSlice = questionRepository.findAllByCategoryAndTodayComments(category, startOfDay, endOfDay, pageable);
        }

        List<QuestionResponse> questionResponseList = questionSlice.stream()
                .map(QuestionResponse::of)
                .toList();

        return new QuestionListResponse(questionResponseList, questionSlice.hasNext());
    }

    @Transactional // 질문 상세 정보 조회는 사용자가 작성한 질문들 중에서만, 고정 질문 X
    public QuestionDetailResponse getQuestionDetail(Long userId, Long questionId) {
        Question question = questionRepository.findByIdAndIsFixedIsFalse(questionId)
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_PARAMETER, "질문 ID가 잘못 되었습니다."));
      
        boolean isAuthor = false;
        if (question.getUser() != null) isAuthor = question.getUser().getId().equals(userId);
      
        return QuestionDetailResponse.of(question, isAuthor);
    }

    @Transactional
    public QuestionListResponse getMyQuestions(Long userId, Pageable pageable) {
        // todo 추후 여기도 페이징 조회 처리
        List<Question> questions = questionRepository.findByUserId(userId);
        List<QuestionResponse> questionResponses = questions.stream()
                .map(QuestionResponse::of)
                .toList();
        return new QuestionListResponse(questionResponses, false);
    }

//    @Transactional
//    public CategoryListResponse getAllCategories() {
//        List<Category> categories = categoryRepository.findAll();
//        List<CategoryResponse> categoryResponses = categories.stream()
//                .map(CategoryResponse::of)
//                .toList();
//        return new CategoryListResponse(categoryResponses);
//    }

    @Transactional
    public QuestionListResponse getQuestionUserAnswered(Long userId, String category, Pageable pageable) {
        Slice<Question> questionSlice = questionRepository.findAllCommentedQuestionsByUserIdAndCategory(userId, category, pageable);
        List<QuestionResponse> questionResponses = questionSlice.stream()
                .map(QuestionResponse::of)
                .toList();
        return new QuestionListResponse(questionResponses, questionSlice.hasNext());
    }

    @Transactional
    public Boolean isExistQuestion(Long questionId) {
        return questionRepository.findQuestionById(questionId).isPresent();
    }

    @Transactional
    public List<CategoryResponse> getAllCategories() {
        List<CategoryResponseDao> categoryResponseDaos = questionRepository.findCategoriesWithCount();
        return categoryResponseDaos.stream()
            .map(dao-> CategoryResponse.of(dao.getCategory(), dao.getCount()))
            .toList();
    }

}
