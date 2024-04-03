package soongsil.kidbean.server.quiz.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import soongsil.kidbean.server.global.exception.errorcode.ErrorCode;

@Getter
@RequiredArgsConstructor
public class ImageQuizSolvedNotFoundException extends RuntimeException {

    private final ErrorCode errorCode;
}
