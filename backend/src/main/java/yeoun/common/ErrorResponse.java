package yeoun.common;

import lombok.Getter;

@Getter
public class ErrorResponse {

    private final String code;

    private String message;

    private Object data;

    public ErrorResponse(String code) {
        this.code = code;
    }

    public ErrorResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public ErrorResponse(String code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

}
