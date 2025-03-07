package com.example.demo.service;

import com.example.demo.dto.request.AddCommentRequestDto;
import com.example.demo.entity.QuestionEntity;
import com.example.demo.repository.CommentRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    public List<?> getAllComments(Long questionId){

    }

    public void addComment(AddCommentRequestDto addCommentRequestDto) {

    }

    public void updateComment(Long commentId, AddCommentRequestDto requestDto) {

    }

    public void deleteComment(Long commentId) {

    }
}
