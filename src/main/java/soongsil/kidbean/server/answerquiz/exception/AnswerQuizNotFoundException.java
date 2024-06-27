package soongsil.kidbean.server.answerquiz.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import soongsil.kidbean.server.global.exception.errorcode.ErrorCode;

@Getter
@RequiredArgsConstructor
public class AnswerQuizNotFoundException extends RuntimeException {

    private final ErrorCode errorCode;
}
