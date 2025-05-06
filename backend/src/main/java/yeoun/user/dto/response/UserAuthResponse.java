package yeoun.user.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import yeoun.user.domain.User;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserAuthResponse {

    private String oAuthPlatform;
    private String email;
    private int questionCount;

    public static UserAuthResponse of(User user) {
        UserAuthResponse vo = new UserAuthResponse();
        vo.oAuthPlatform = user.getOAuthPlatform();
        vo.email = user.getEmail();
        vo.questionCount = user.getQuestionCount();
        return vo;
    }
}
