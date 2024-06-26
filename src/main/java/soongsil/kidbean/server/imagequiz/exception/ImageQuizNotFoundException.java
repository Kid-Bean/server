package soongsil.kidbean.server.imagequiz.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import soongsil.kidbean.server.global.exception.errorcode.ErrorCode;

@Getter
@RequiredArgsConstructor
public class ImageQuizNotFoundException extends RuntimeException {

    private final ErrorCode errorCode;
}
