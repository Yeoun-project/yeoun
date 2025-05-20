package yeoun.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserWithdrawRequest {

    @NotBlank @NotEmpty
    private final String reasonCategory;

    private String reasonDetail;

}
