package soongsil.kidbean.server.auth.exception.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import soongsil.kidbean.server.global.exception.errorcode.ErrorCode;

@Getter
@RequiredArgsConstructor
public enum OAuthErrorCode implements ErrorCode {
    LOGIN_TYPE_NOT_SUPPORT(HttpStatus.NOT_ACCEPTABLE, "login type not support."),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
