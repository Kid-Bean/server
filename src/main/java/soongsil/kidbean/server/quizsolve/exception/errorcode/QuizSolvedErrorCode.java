package soongsil.kidbean.server.quizsolve.exception.errorcode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import soongsil.kidbean.server.global.exception.errorcode.ErrorCode;

@Getter
@RequiredArgsConstructor
public enum QuizSolvedErrorCode implements ErrorCode {
    QUIZ_SOLVED_NOT_FOUND(HttpStatus.NOT_FOUND, "QuizSolved not found"),
    ANSWER_QUIZ_SOLVED_NOT_FOUND(HttpStatus.NOT_FOUND, "AnswerQuizSolved not found"),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
