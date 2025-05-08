package yeoun.user.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class IsNotificationRequest {

    @NotNull(message = "is Notification값을 필수입니다")
    private final Boolean isNotification;

}
