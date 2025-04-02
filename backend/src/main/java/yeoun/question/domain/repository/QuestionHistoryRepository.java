package yeoun.question.domain.repository;

import yeoun.question.domain.QuestionHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionHistoryRepository extends JpaRepository<QuestionHistoryEntity, Long> {
}
