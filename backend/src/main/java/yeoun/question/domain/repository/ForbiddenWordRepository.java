package yeoun.question.domain.repository;

import yeoun.question.domain.ForbiddenWordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ForbiddenWordRepository extends JpaRepository<ForbiddenWordEntity, Long> {

}
