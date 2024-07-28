package soongsil.kidbean.server.wordquiz.exception.errorcode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import soongsil.kidbean.server.global.exception.errorcode.ErrorCode;

@Getter
@RequiredArgsConstructor
public enum WordQuizErrorCode implements ErrorCode {
    WORD_QUIZ_NOT_FOUND(HttpStatus.NOT_FOUND, "WordQuiz not found"),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
