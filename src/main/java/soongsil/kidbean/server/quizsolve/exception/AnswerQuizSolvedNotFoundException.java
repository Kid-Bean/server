package soongsil.kidbean.server.quizsolve.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import soongsil.kidbean.server.global.exception.errorcode.ErrorCode;

@Getter
@RequiredArgsConstructor
public class AnswerQuizSolvedNotFoundException extends RuntimeException {
    private final ErrorCode errorCode;
}
