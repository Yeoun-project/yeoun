package yeoun.comment.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.transaction.annotation.Transactional;
import yeoun.auth.service.JwtService;
import yeoun.comment.dto.request.SaveCommentRequest;
import yeoun.comment.domain.Comment;
import yeoun.comment.dto.response.CommentListResponse;
import yeoun.comment.dto.response.CommentResponse;
import yeoun.notification.domain.NotificationType;
import yeoun.notification.service.NotificationService;
import yeoun.question.domain.Question;
import yeoun.question.domain.repository.QuestionRepository;
import yeoun.question.service.QuestionService;
import yeoun.user.domain.User;
import yeoun.exception.CustomException;
import yeoun.exception.ErrorCode;
import yeoun.comment.domain.repository.CommentRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final EntityManager entityManager;
    private final QuestionService questionService;
    private final CommentRepository commentRepository;
    private final NotificationService notificationService;
    private final QuestionRepository questionRepository;

    @Transactional
    public Comment saveComment(SaveCommentRequest commentDto) {
        Long userId = JwtService.getUserIdFromAuthentication();
        commentDto.setUserId(userId);

        Question targetQuestion = questionRepository.findQuestionById(commentDto.getQuestionId())
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_PARAMETER, "Invalid question id")
        );

        if (targetQuestion.getDeleteTime() != null)
            throw new CustomException(ErrorCode.CONFLICT, "작성자가 떠난 질문은 답변 등록이 불가합니다");

        User writer = targetQuestion.getUser();

        if (writer != null && Objects.equals(writer.getId(), commentDto.getUserId())) {
            throw new CustomException(ErrorCode.BAD_REQUEST, "본인의 게시글에 답변을 등록할 수 없습니다");
        }

        Optional<Comment> comment = commentRepository.getCommentByUserId(userId, commentDto.getQuestionId());

        if (comment.isPresent())
            throw new CustomException(ErrorCode.ALREADY_EXIST, "이미 존재합니다");

        if (writer != null)
            notificationService.addNotification(userId, targetQuestion.getUser().getId(), NotificationType.NEW_COMMENT, commentDto.getQuestionId());

        return commentRepository.save(Comment.builder()
                .id(commentDto.getId())
                .content(commentDto.getContent())
                .question(entityManager.getReference(Question.class, commentDto.getQuestionId()))
                .user(entityManager.getReference(User.class, commentDto.getUserId()))
                .build());
    }

    @Transactional
    public void updateComment(SaveCommentRequest commentDto) {
        Optional<Comment> commentOptional = commentRepository.getCommentById(commentDto.getId());

        if (commentOptional.isEmpty())
            throw new CustomException(ErrorCode.NOT_FOUND, "존재하지 않습니다");

        User user = commentOptional.get().getUser();

        if (!Objects.equals(user.getId(), commentDto.getUserId()))
            throw new CustomException(ErrorCode.INVALID_PARAMETER, "작성자가 아닙니다");

        commentRepository.save(Comment.builder()
                .id(commentDto.getId())
                .content(commentDto.getContent())
                .question(commentOptional.get().getQuestion())
                .user(user)
                .build());
    }

    @Transactional
    public void deleteComment(Long commentId, Long userId) {
        Optional<Comment> commentOptional = commentRepository.getCommentById(commentId);

        if (commentOptional.isEmpty())
            throw new CustomException(ErrorCode.NOT_FOUND, "존재하지 않습니다");

        if (!Objects.equals(commentOptional.get().getUser().getId(), userId))
            throw new CustomException(ErrorCode.BAD_REQUEST, "작성자가 아닙니다");

        commentRepository.deleteById(commentId);
    }

    @Transactional(readOnly = true)
    public CommentListResponse getAllComments(Long questionId, Long userId, Pageable pageable) {
        Boolean isExistQuestion = questionService.isExistQuestion(questionId);
        if (!isExistQuestion) throw new CustomException(ErrorCode.INVALID_PARAMETER, "Invalid question id");

        Optional<Comment> myCommentOpt = commentRepository.findTopByUserIdAndQuestionId(userId, questionId);
        CommentResponse myCommentResponse = myCommentOpt
                .map(comment -> CommentResponse.of(comment, false))
                .orElse(null);

        boolean isLikeSort = pageable.getSort().getOrderFor("LIKE") != null;
        Slice<Comment> comments;

        if (isLikeSort) {
            Pageable noSortPageable = PageRequest.of(
                    pageable.getPageNumber(),
                    pageable.getPageSize()
            );

            comments = commentRepository.findAllByQuestionIdExcludeMineOrderByLikeCountDesc(questionId, userId, noSortPageable);
        } else {
            comments = commentRepository.getAllCommentByQuestionExcludeMyself(questionId, userId, pageable);
        }

        final List<CommentResponse> commentResponses = comments.stream()
                .map(comment -> CommentResponse.of(
                        comment,
                        isLikedByUser(comment, userId)
                )).toList();

        return new CommentListResponse(
                myCommentResponse,
                commentResponses,
                comments.hasNext()
        );
    }

    private boolean isLikedByUser(final Comment comment, final Long userId) {
        return comment.getLikes().stream()
                .anyMatch(like -> like.getUser().getId().equals(userId));
    }


}
