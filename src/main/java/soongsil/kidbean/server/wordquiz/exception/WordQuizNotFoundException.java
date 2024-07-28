package soongsil.kidbean.server.wordquiz.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import soongsil.kidbean.server.global.exception.errorcode.ErrorCode;

@Getter
@RequiredArgsConstructor
public class WordQuizNotFoundException extends RuntimeException {

    private final ErrorCode errorCode;
}
