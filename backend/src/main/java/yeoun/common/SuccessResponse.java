package yeoun.common;

import lombok.Getter;

@Getter
public class SuccessResponse {

    private String message;

    private final Object data;

    public SuccessResponse(Object data) {
        this.data = data;
    }

    public SuccessResponse(String message, Object data) {
        this.message = message;
        this.data = data;
    }
}
