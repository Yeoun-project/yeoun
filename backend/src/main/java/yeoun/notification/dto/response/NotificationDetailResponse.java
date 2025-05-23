package yeoun.notification.dto.response;

import java.time.LocalDateTime;

import lombok.*;
import yeoun.notification.domain.Notification;
import yeoun.notification.domain.NotificationType;

@Getter
@Builder
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class NotificationDetailResponse {

    private final Long questionId;

    private final String content;

    private final LocalDateTime createTime;

    private final String category;

    public static NotificationDetailResponse of(
            Notification notification
    ) {
        return NotificationDetailResponse.builder()
            .questionId(notification.getQuestion().getId())
            .content(
                    NotificationType.getContent(
                            notification.getNotificationType(),
                            notification.getQuestion().getContent(),
                            notification.getCount(),
                            notification.getSender().getName()
                    )
            )
            .createTime(notification.getCreateTime())
            .category(notification.getQuestion().getCategory().getName())
            .build();
    }

}
