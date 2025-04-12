package yeoun.notification.dto.response;

import yeoun.notification.domain.Notification;
import yeoun.notification.domain.NotificationType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationResponse {

    private Long id;

    private String content;

    public static NotificationResponse of(Notification entity) {
        NotificationResponse response = new NotificationResponse();
        response.id = entity.getId();
        response.content = entity.getNotificationType().getContent(entity.getQuestion().getContent());
        return response;
    }

}
