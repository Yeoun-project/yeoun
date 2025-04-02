package yeoun.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    BAD_REQUEST(400, "BAD_REQUEST"),
    INVALID_PARAMETER(400, "INVALID_PARAMETER"),
    MISSING_PARAMETER(400, "MISSING_PARAMETER"),
    UNAUTHORIZED(401, "UNAUTHORIZED"),
    NOT_FOUND(404, "NOT_FOUND"),
    CONFLICT(409, "CONFLICT"),
    ALREADY_EXIST(409, "ALREADY_EXIST"),
    DELETED(410, "DELETED"),
    INTERNAL_SERVER_ERROR(500, "INTERNAL_SERVER_ERROR");

    private final int httpStatusCode;

    private final String code;

}
