package soongsil.kidbean.server.auth.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import soongsil.kidbean.server.global.exception.errorcode.ErrorCode;

@Getter
@RequiredArgsConstructor
public class LoginTypeNotSupportException extends RuntimeException {
    private final ErrorCode errorCode;
}
