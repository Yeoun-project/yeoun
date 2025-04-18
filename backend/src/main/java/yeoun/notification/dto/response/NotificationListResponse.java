package yeoun.notification.dto.response;

import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Slice;
import yeoun.notification.domain.Notification;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NotificationListResponse {

    private List<NotificationDetailResponse> details;

    private boolean hasNext;

    @Builder
    public NotificationListResponse(List<Notification> details, boolean hasNext) {
        this.details = details.stream().map(NotificationDetailResponse::of).toList();
        this.hasNext = hasNext;
    }
}
