package yeoun.notification.dto.response;

import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Slice;
import yeoun.notification.domain.Notification;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NotificationList {

    private List<NotificationDetail> details;

    private boolean hasNext;

    public static NotificationList of(Slice<Notification> details) {
        NotificationList list = new NotificationList();
        list.details = details.stream().map(NotificationDetail::of).toList();
        list.hasNext = details.hasNext();
        return list;
    }
}
