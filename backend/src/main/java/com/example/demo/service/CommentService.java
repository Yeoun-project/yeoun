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

    @Transactional
    public void updateComment(SaveCommentRequestDto commentDto) {
        if(commentRepository.update(commentDto.getId(), commentDto.getContent(), commentDto.getUserId()) == 0)
            throw new IllegalArgumentException(String.format("commentId(%d)'s Author is not userId(%d)", commentDto.getId(), commentDto.getUserId()));
    }

    @Transactional
    public void deleteComment(Long commentId, Long userId) {
        if(commentRepository.delete(commentId, userId) == 0)
            throw new IllegalArgumentException(String.format("commentId(%d)'s Author is not userId(%d)", commentId, userId));
    }
}
