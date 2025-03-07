package com.example.demo.service;

<<<<<<< HEAD:backend/src/main/java/com/example/demo/service/CommentService.java
import com.example.demo.entity.QuestionEntity;
import com.example.demo.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

=======
import com.example.demo.dto.request.AddCommentRequestDto;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    public List<?> getAllComments(Long questionId){

    }

    public void addComment(AddCommentRequestDto addCommentRequestDto) {

    }

    public void updateComment(Long commentId, AddCommentRequestDto requestDto) {

    }

    public void deleteComment(Long commentId) {

    }
>>>>>>> 7bb2900 (front back repo 병합으로인한 remain작업 전 commit):src/main/java/com/example/demo/service/CommentService.java
}
