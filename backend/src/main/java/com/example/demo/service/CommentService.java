package com.example.demo.service;

import com.example.demo.dto.request.SaveCommentRequestDto;
import com.example.demo.entity.CommentEntity;
import com.example.demo.entity.QuestionEntity;
import com.example.demo.entity.UserEntity;
import com.example.demo.repository.CommentRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final EntityManager entityManager;
    private final CommentRepository commentRepository;

    public CommentEntity saveComment(SaveCommentRequestDto commentDto) {
        return commentRepository.save(CommentEntity.builder()
                .id(commentDto.getId())
                .content(commentDto.getContent())
                .question(entityManager.getReference(QuestionEntity.class, commentDto.getQuestionId()))
                .user(entityManager.getReference(UserEntity.class, commentDto.getUserId()))
                .build());
    }

    public void updateComment(SaveCommentRequestDto commentDto) {
        if(saveComment(commentDto) == null)
            // comment update의 결과가 없음 => update요청한 댓글의 작성자가 userId가 아님
            throw new IllegalArgumentException(String.format("commentId(%L)'s Author is not userId(%L)", commentDto.getId(), commentDto.getUserId()));
    }

    @Transactional
    public void deleteComment(Long commentId) {
        commentRepository.delete(commentId);
    }
}
