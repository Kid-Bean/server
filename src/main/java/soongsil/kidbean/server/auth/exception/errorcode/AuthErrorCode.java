package soongsil.kidbean.server.auth.exception.errorcode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import soongsil.kidbean.server.global.exception.errorcode.ErrorCode;

@Getter
@RequiredArgsConstructor
public enum AuthErrorCode implements ErrorCode {
    LOGIN_TYPE_NOT_SUPPORT(HttpStatus.NOT_ACCEPTABLE, "login type not support."),
    TOKEN_NOT_VALID(HttpStatus.NOT_ACCEPTABLE, "refresh token is not valid."),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
