package yeoun.notification.dto.response;

import java.util.List;

import lombok.*;
import org.springframework.data.domain.Slice;
import yeoun.notification.domain.Notification;

@Getter
@RequiredArgsConstructor
public class NotificationListResponse {

    private final List<NotificationDetailResponse> details;

    private final boolean hasNext;

}
