package yeoun.notification.dto.response;

import java.time.LocalDateTime;

import lombok.*;
import yeoun.notification.domain.NotificationType;
import yeoun.notification.domain.repository.NotificationDao;

@Getter
@Builder
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class NotificationResponse {

    private final Long questionId;
    private final String content;
    private final LocalDateTime createAt;
    private final int count;

    public static NotificationResponse of(
            NotificationDao notificationDao
    ) {
        return NotificationResponse.builder()
                .questionId(notificationDao.getQuestion().getId())
                .content(NotificationType.getContent(
                        notificationDao.getType(),
                        notificationDao.getQuestion().getContent(),
                        notificationDao.getCount(),
                        notificationDao.getSender()
                ))
                .createAt(notificationDao.getCreatedAt())
                .count(notificationDao.getCount())
                .build();
    }

}
