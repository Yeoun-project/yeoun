package com.example.demo.repository;

import com.example.demo.entity.QuestionEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<QuestionEntity, Long> {
    @Query("SELECT q FROM QuestionEntity q LEFT JOIN q.comments c GROUP BY q ORDER BY COUNT(c) DESC")
    List<QuestionEntity> findAllOrderByCommentsCountDesc();

    @Query("SELECT q FROM QuestionEntity q LEFT JOIN FETCH q.comments WHERE q.id = :questionId")
    Optional<QuestionEntity> findQuestionWithCommentById(@Param("questionId") Long questionId);

}
