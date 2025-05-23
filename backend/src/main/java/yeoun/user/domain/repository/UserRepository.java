package yeoun.user.domain.repository;

import java.time.LocalDateTime;
import java.util.List;
import yeoun.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE u.oAuthId = :oAuthId")
    Optional<User> findByOAuthId(@Param("oAuthId") String oAuthId);

    @Query("update User as u set u.uuid = :uuid where u.id = :userId")
    String updateUUID(@Param("userId") String userId, @Param("uuid") String uuid);

    @Modifying
    @Query("update User u set u.isNotification = :isNotification where u.id = :userId")
    void setIsNotification(@Param("isNotification") boolean isNotification,@Param("userId") long userId);

    @Modifying
    @Query("delete from User u where u.id not in (select h.user.id from UserHistory h group by h.user.id) and u.role = 'ANONYMOUS' and u.createTime < :deleteTime")
    void deleteOldAnonymousUser(@Param("deleteTime") LocalDateTime deleteTime);

    @Modifying
    @Query("update User u set u.questionCount = u.questionCount -1 where u.id = :userId")
    void setQuestionCount(@Param("userId") long userId);

    @Modifying
    @Query("update User u set u.questionCount =  :count where u.role in ('USER', 'ADMIN') ")
    void setAllUserQuestionCount(@Param("count") int count);
  
}

