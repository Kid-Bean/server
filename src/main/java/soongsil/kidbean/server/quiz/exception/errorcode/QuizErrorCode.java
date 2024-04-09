package soongsil.kidbean.server.quiz.exception.errorcode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import soongsil.kidbean.server.global.exception.errorcode.ErrorCode;

@Getter
@RequiredArgsConstructor
public enum QuizErrorCode implements ErrorCode {
    IMAGE_QUIZ_NOT_FOUND(HttpStatus.NOT_FOUND, "ImageQuiz not found"),
    IMAGE_QUIZ_SOLVED_NOT_FOUND(HttpStatus.NOT_FOUND, "ImageQuizSolved not found"),
    Word_QUIZ_NOT_FOUND(HttpStatus.NOT_FOUND, "WordQuiz not found"),
    ANSWER_QUIZ_NOT_FOUND(HttpStatus.NOT_FOUND, "AnswerQuiz not found"),
    ANSWER_QUIZ_SOLVED_NOT_FOUND(HttpStatus.NOT_FOUND, "AnswerQuizSolved not found"),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
