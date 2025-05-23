package yeoun.user.service;

import yeoun.user.domain.User;
import yeoun.user.domain.UserHistory;
import yeoun.user.domain.repository.UserHistoryRepository;
import yeoun.user.domain.repository.UserRepository;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserHistoryService {

    @Value("${scheduler.user_history_delete_second}")
    private Long USER_HISTORY_DELETE_SECOND;

    private final UserRepository userRepository;
    private final UserHistoryRepository userHistoryRepository;
    private final EntityManager entityManager;

    public void addHistory(Long userId, String ip, String agent, String uri) {
        userHistoryRepository.save(
            UserHistory
                    .builder()
                    .user(entityManager.getReference(User.class, userId))
                    .agent(agent)
                    .ip(ip)
                    .uri(uri)
                    .build());
    }

    // scheduler
//    @Transactional
//    @Scheduled(cron="${scheduler.cron}")
//    public void deleteOldUserHistory() {
//        userHistoryRepository.deleteOldHistory(deleteTime);
//        userRepository.deleteOldAnonymousUser();
//    }
}
