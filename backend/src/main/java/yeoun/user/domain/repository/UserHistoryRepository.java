package yeoun.user.domain.repository;

import yeoun.user.domain.UserHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserHistoryRepository extends JpaRepository<UserHistory, Long> {

    @Modifying
    @Query(value = "delete from user_history where last_login < NOW() - INTERVAL :deleteTime SECOND", nativeQuery = true)
    void deleteOldHistory(@Param("deleteTime") long deleteTime);

}
