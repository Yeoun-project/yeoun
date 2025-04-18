package yeoun.notification.dto.response;

import java.time.LocalDateTime;
import java.util.Date;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import yeoun.notification.domain.Notification;
import yeoun.notification.domain.NotificationType;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NotificationDetail {

    private Long questionId;

    private String content;

    private LocalDateTime createAt;


    public static NotificationDetail of(Notification entity) {
        NotificationDetail response = new NotificationDetail();
        response.questionId = entity.getQuestion().getId();
        response.content = NotificationType.getContent(entity.getNotificationType(), entity.getQuestion().getContent(), entity.getCount(), entity.getSender().getName());
        response.createAt = entity.getCreatedAt();
        return response;
    }

}
