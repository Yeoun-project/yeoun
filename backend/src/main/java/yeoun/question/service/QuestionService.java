package yeoun.question.service;

import java.util.Optional;

import yeoun.comment.domain.CommentEntity;
import yeoun.comment.service.CommentLikeService;
import yeoun.comment.service.CommentService;
import yeoun.question.dto.request.AddQuestionRequest;
import yeoun.question.domain.CategoryEntity;
import yeoun.question.domain.QuestionEntity;
import yeoun.question.dto.response.QuestionDetailResponse;
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
import java.util.Set;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final ForbiddenWordService forbiddenWordService;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final QuestionRepository questionRepository;
    private final CommentService commentService;
    private final CommentLikeService commentLikeService;

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

    public QuestionDetailResponse getQuestionDetail(Long questionId, Long userId) {
        QuestionEntity question = questionRepository.findQuestionWithCommentById(questionId)
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_PARAMETER, "Invalid question ID"));
        CommentEntity myComment = commentService.getMyCommentByQuestionId(userId, questionId);

        List<Long> commentIds = question.getComments().stream().map(CommentEntity::getId).toList();
        Set<Long> likedCommentIds = commentLikeService.getLikedCommentIdSetByUser(userId, commentIds);

        List<QuestionDetailResponse.Comment> commentDtoList = question.getComments().stream()
                .map(comment -> {
                    boolean isLiked = likedCommentIds.contains(comment.getId());
                    return QuestionDetailResponse.Comment.builder()
                            .id(comment.getId())
                            .content(comment.getContent())
                            .createTime(comment.getCreatedDateTime())
                            .like(isLiked)
                            .build();
                })
                .toList();

        QuestionDetailResponse.QuestionDetailResponseBuilder builder = QuestionDetailResponse.builder()
                .question(QuestionDetailResponse.Question.builder()
                        .id(question.getId())
                        .content(question.getContent())
                        .heart(question.getHeart())
                        .commentCount(question.getComments().size())
                        .categoryName(question.getCategory().getName())
                        .createTime(question.getCreatedDateTime())
                        .author(question.getUser().getId().equals(userId))
                        .build())
                .comments(commentDtoList);

        if (myComment != null) {
            builder.myComment(QuestionDetailResponse.MyComment.builder()
                    .id(myComment.getId())
                    .content(myComment.getContent())
                    .createTime(myComment.getCreatedDateTime())
                    .build());
        }

        return builder.build();
    }

    public List<QuestionEntity> getQuestionsByUserId(long userId) {
        return questionRepository.findByUserId(userId);
    }

    public List<CategoryEntity> getAllCategories() {
        return categoryRepository.findAll();
    }
}
