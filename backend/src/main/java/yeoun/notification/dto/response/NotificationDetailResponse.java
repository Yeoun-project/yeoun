package yeoun.notification.dto.response;

import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import yeoun.notification.domain.Notification;
import yeoun.notification.domain.NotificationType;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class NotificationDetailResponse {

    private Long questionId;

    private String content;

    private LocalDateTime createTime;

    public static NotificationDetailResponse of(Notification entity) {
        return NotificationDetailResponse.builder()
            .questionId(entity.getQuestion().getId())
            .content(NotificationType.getContent(entity.getNotificationType(), entity.getQuestion().getContent(), entity.getCount(), entity.getSender().getName()))
            .createTime(entity.getCreateTime())
            .build();
    }

}
