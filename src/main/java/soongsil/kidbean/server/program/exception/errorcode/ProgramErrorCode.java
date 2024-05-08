package soongsil.kidbean.server.program.exception.errorcode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import soongsil.kidbean.server.global.exception.errorcode.ErrorCode;

@Getter
@RequiredArgsConstructor
public enum ProgramErrorCode implements ErrorCode {

    PROGRAM_NOT_FOUND(HttpStatus.NOT_FOUND, "Program not found");

    private final HttpStatus httpStatus;
    private final String message;
}
