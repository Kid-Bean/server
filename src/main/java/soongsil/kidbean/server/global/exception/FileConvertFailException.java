package soongsil.kidbean.server.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import soongsil.kidbean.server.global.exception.errorcode.ErrorCode;

@Getter
@RequiredArgsConstructor
public class FileConvertFailException extends RuntimeException {

    private final ErrorCode errorCode;
}
