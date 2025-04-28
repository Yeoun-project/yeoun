package yeoun.user.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import yeoun.user.domain.User;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserNotificationResponse {

    private Boolean isNotification;

    public static UserNotificationResponse of(User user) {
        UserNotificationResponse vo = new UserNotificationResponse();
        vo.isNotification = user.getIsNotification();
        return vo;
    }
}
