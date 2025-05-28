package yeoun.survey.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yeoun.survey.domain.Survey;

public interface SurveyRepository extends JpaRepository<Survey, Long> {
}
