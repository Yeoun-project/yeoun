package yeoun.comment.service;

import java.util.List;
import java.util.Optional;
import yeoun.auth.service.JwtService;
import yeoun.comment.dto.request.SaveCommentRequest;
import yeoun.comment.domain.CommentEntity;
import yeoun.question.domain.QuestionEntity;
import yeoun.user.domain.UserEntity;
import yeoun.exception.CustomException;
import yeoun.exception.ErrorCode;
import yeoun.comment.domain.repository.CommentRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final EntityManager entityManager;
    private final CommentRepository commentRepository;

    public CommentEntity saveComment(SaveCommentRequest commentDto) {
        Long userId = JwtService.getUserIdFromAuthentication();
        commentDto.setUserId(userId);

        Optional<CommentEntity> comment = commentRepository.getCommentByUserId(userId, commentDto.getQuestionId());

        if(comment.isPresent())
            throw new CustomException(ErrorCode.ALREADY_EXIST, "이미 존재합니다");

        return commentRepository.save(CommentEntity.builder()
                .id(commentDto.getId())
                .content(commentDto.getContent())
                .question(entityManager.getReference(QuestionEntity.class, commentDto.getQuestionId()))
                .user(entityManager.getReference(UserEntity.class, commentDto.getUserId()))
                .build());
    }

    @Transactional
    public void updateComment(SaveCommentRequest commentDto) {
        Optional<CommentEntity> commentOptional = commentRepository.getCommentById(commentDto.getId());

        if(commentOptional.isEmpty())
            throw new CustomException(ErrorCode.NOT_FOUND, "존재하지 않습니다");

        UserEntity user = commentOptional.get().getUser();

        if(user.getId() != commentDto.getUserId())
            throw new CustomException(ErrorCode.INVALID_PARAMETER, "작성자가 아닙니다");

        commentRepository.save(CommentEntity.builder()
                .id(commentDto.getId())
                .content(commentDto.getContent())
                .question(commentOptional.get().getQuestion())
                .user(user)
                .build());
    }

    @Transactional
    public void deleteComment(Long commentId, Long userId) {
        Optional<CommentEntity> commentOptional = commentRepository.getCommentById(commentId);

        if(commentOptional.isEmpty())
            throw new CustomException(ErrorCode.NOT_FOUND, "존재하지 않습니다");

        if(commentOptional.get().getUser().getId() != userId)
            throw new CustomException(ErrorCode.BAD_REQUEST, "작성자가 아닙니다");

        commentRepository.deleteById(commentId);
    }

    public List<CommentEntity> getCommentsByUserId(Long userId) {
        return commentRepository.getCommentsById(userId);
    }

    public CommentEntity getMyCommentByQuestionId(Long userId, Long questionId) {
        return commentRepository.getCommentByUserIdAndQuestionId(userId, questionId).orElse(null);
    }
}
