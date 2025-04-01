package yeoun.notification.dto.response;

import yeoun.notification.domain.NotificationType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationResponse {

    private Long id;

    private String content;

    private NotificationType type;

    public NotificationResponse(String content, NotificationType type) {
        this.content = content; this.type = type;
    }
    public NotificationResponse(Long id, String content, NotificationType type) {
        this.content = content; this.type = type; this.id = id;
    }

    @Override
    public String toString() {
        return String.format("{'content':'%s', 'type':'%s'}", content, type.type);
    }

}
