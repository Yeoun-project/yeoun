package yeoun.user.vo;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserRegisterInfoVo {

    private String oAuthId;

    private String oAuthPlatform;

    private String name;

    private String email;

    private String phone;

}
