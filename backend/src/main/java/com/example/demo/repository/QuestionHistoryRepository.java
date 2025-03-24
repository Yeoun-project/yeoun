package com.example.demo.repository;

import com.example.demo.entity.QuestionHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionHistoryRepository extends JpaRepository<QuestionHistoryEntity, Long> {
}
