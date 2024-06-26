package soongsil.kidbean.server.program.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import soongsil.kidbean.server.global.exception.errorcode.ErrorCode;

@Getter
@RequiredArgsConstructor
public class ProgramNotFoundException extends RuntimeException {

    private final ErrorCode errorCode;
}
