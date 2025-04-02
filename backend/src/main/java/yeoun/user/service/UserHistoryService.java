package yeoun.user.service;

import yeoun.user.domain.UserEntity;
import yeoun.user.domain.UserHistoryEntity;
import yeoun.user.domain.repository.UserHistoryRepository;
import yeoun.user.domain.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserHistoryService {

    @Value("${scheduler.user_history_delete_second}")
    private Long deleteTime;

    private final UserRepository userRepository;
    private final UserHistoryRepository userHistoryRepository;
    private final EntityManager entityManager;

    public void addHistory(Long userId, String ip, String agent, String uri) {
        userHistoryRepository.save(
            UserHistoryEntity
                    .builder()
                    .user(entityManager.getReference(UserEntity.class, userId))
                    .agent(agent)
                    .ip(ip)
                    .uri(uri)
                    .build());
    }

    // scheduler
    @Transactional
    @Scheduled(cron="${scheduler.cron}")
    public void deleteOldUserHistory() {
        userHistoryRepository.deleteOldHistory(deleteTime);
        userRepository.deleteOldAnonymousUser();
    }
}
