package yeoun.notification.dto.response;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import yeoun.notification.domain.NotificationType;
import yeoun.notification.domain.repository.NotificationDao;

@Getter
@Setter
public class NotificationResponseDto {

    private Long questionId;

    private String content;

    private Date createAt;

    private int count;

    public static NotificationResponseDto of(NotificationDao dao) {
        NotificationResponseDto response = new NotificationResponseDto();
        response.questionId = dao.getQuestion().getId();
        response.content = NotificationType.getContent(dao.getType(), dao.getQuestion().getContent(), dao.getCount(), dao.getSender());
        response.createAt = dao.getCreatedAt();
        response.count = dao.getCount();
        return response;
    }

}
