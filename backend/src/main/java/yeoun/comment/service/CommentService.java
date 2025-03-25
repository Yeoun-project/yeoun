package yeoun.comment.service;

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
        return commentRepository.save(CommentEntity.builder()
                .id(commentDto.getId())
                .content(commentDto.getContent())
                .question(entityManager.getReference(QuestionEntity.class, commentDto.getQuestionId()))
                .user(entityManager.getReference(UserEntity.class, commentDto.getUserId()))
                .build());
    }

    @Transactional
    public void updateComment(SaveCommentRequest commentDto) {
        if(commentRepository.update(commentDto.getId(), commentDto.getContent(), commentDto.getUserId()) == 0)
            throw new CustomException(ErrorCode.CONFLICT ,String.format("commentId(%d)'s Author is not userId(%d)", commentDto.getId(), commentDto.getUserId()));
    }

    @Transactional
    public void deleteComment(Long commentId, Long userId) {
        if(commentRepository.delete(commentId, userId) == 0)
            throw new CustomException(ErrorCode.CONFLICT ,String.format("commentId(%d)'s Author is not userId(%d)", commentId, userId));
    }
}
