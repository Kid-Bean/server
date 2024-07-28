package soongsil.kidbean.server.answerquiz.exception.errorcode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import soongsil.kidbean.server.global.exception.errorcode.ErrorCode;

@Getter
@RequiredArgsConstructor
public enum AnswerQuizErrorCode implements ErrorCode {
    ANSWER_QUIZ_NOT_FOUND(HttpStatus.NOT_FOUND, "AnswerQuiz not found"),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
