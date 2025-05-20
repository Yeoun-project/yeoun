package yeoun.withdrawHistory.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import yeoun.withdrawHistory.domain.WithdrawHistory;

@Repository
public interface WithdrawHistoryRepository extends JpaRepository<WithdrawHistory, Long> {
}
