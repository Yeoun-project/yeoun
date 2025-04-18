package yeoun.notification.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class NotificationListResponse {

    private final List<NotificationResponse> notifications;

}
