package soongsil.kidbean.server.global.exception.handler;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import soongsil.kidbean.server.auth.exception.LoginTypeNotSupportException;
import soongsil.kidbean.server.auth.exception.TokenNotValidException;
import soongsil.kidbean.server.global.exception.FileConvertFailException;
import soongsil.kidbean.server.global.exception.errorcode.ErrorCode;
import soongsil.kidbean.server.global.exception.errorcode.GlobalErrorCode;
import soongsil.kidbean.server.global.exception.response.ErrorResponse;
import soongsil.kidbean.server.global.exception.response.ErrorResponse.ValidationError;
import soongsil.kidbean.server.global.exception.response.ErrorResponse.ValidationErrors;
import soongsil.kidbean.server.member.exception.MemberNotFoundException;
import soongsil.kidbean.server.program.exception.ProgramNotFoundException;
import soongsil.kidbean.server.answerquiz.exception.AnswerQuizNotFoundException;
import soongsil.kidbean.server.quizsolve.exception.AnswerQuizSolvedNotFoundException;
import soongsil.kidbean.server.imagequiz.exception.ImageQuizNotFoundException;
import soongsil.kidbean.server.quizsolve.exception.QuizSolvedNotFoundException;
import soongsil.kidbean.server.wordquiz.exception.WordQuizNotFoundException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(FileConvertFailException.class)
    public ResponseEntity<Object> handleFileConvertFail(final FileConvertFailException e) {
        final ErrorCode errorCode = e.getErrorCode();
        return handleExceptionInternal(errorCode);
    }

    @ExceptionHandler(AnswerQuizNotFoundException.class)
    public ResponseEntity<Object> handleAnswerQuizNotFound(final AnswerQuizNotFoundException e) {
        final ErrorCode errorCode = e.getErrorCode();
        return handleExceptionInternal(errorCode);
    }

    @ExceptionHandler(ImageQuizNotFoundException.class)
    public ResponseEntity<Object> handleImageQuizNotFound(final ImageQuizNotFoundException e) {
        final ErrorCode errorCode = e.getErrorCode();
        return handleExceptionInternal(errorCode);
    }

    @ExceptionHandler(QuizSolvedNotFoundException.class)
    public ResponseEntity<Object> handleImageQuizSolvedNotFound(final QuizSolvedNotFoundException e) {
        final ErrorCode errorCode = e.getErrorCode();
        return handleExceptionInternal(errorCode);
    }

    @ExceptionHandler(WordQuizNotFoundException.class)
    public ResponseEntity<Object> handleWordQuizNotFound(final WordQuizNotFoundException e) {
        final ErrorCode errorCode = e.getErrorCode();
        return handleExceptionInternal(errorCode);
    }

    @ExceptionHandler(AnswerQuizSolvedNotFoundException.class)
    public ResponseEntity<Object> handleAnswerQuizSolvedNotFound(final AnswerQuizSolvedNotFoundException e) {
        final ErrorCode errorCode = e.getErrorCode();
        return handleExceptionInternal(errorCode);
    }

    @ExceptionHandler(MemberNotFoundException.class)
    public ResponseEntity<Object> handleMemberNotFound(final MemberNotFoundException e) {
        final ErrorCode errorCode = e.getErrorCode();
        return handleExceptionInternal(errorCode);
    }

    @ExceptionHandler(LoginTypeNotSupportException.class)
    public ResponseEntity<Object> handleLoginTypeNotSupport(final LoginTypeNotSupportException e) {
        final ErrorCode errorCode = e.getErrorCode();
        return handleExceptionInternal(errorCode);
    }

    @ExceptionHandler(TokenNotValidException.class)
    public ResponseEntity<Object> handleTokenNotValid(final TokenNotValidException e) {
        final ErrorCode errorCode = e.getErrorCode();
        return handleExceptionInternal(errorCode);
    }

    @ExceptionHandler(ProgramNotFoundException.class)
    public ResponseEntity<Object> handleProgramNotFound(final ProgramNotFoundException e) {
        final ErrorCode errorCode = e.getErrorCode();
        return handleExceptionInternal(errorCode);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgument(final IllegalArgumentException e) {
        log.warn("handleIllegalArgument", e);
        final ErrorCode errorCode = GlobalErrorCode.INVALID_PARAMETER;
        return handleExceptionInternal(errorCode, e.getMessage());
    }

    /**
     * DTO @Valid 관련 exception 처리
     */
    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(
            final MethodArgumentNotValidException e,
            final HttpHeaders headers,
            final HttpStatusCode status,
            final WebRequest request) {
        log.warn("handleIllegalArgument", e);
        final ErrorCode errorCode = GlobalErrorCode.INVALID_PARAMETER;
        return handleExceptionInternal(e, errorCode);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllException(final Exception ex) {
        log.warn("handleAllException", ex);
        final ErrorCode errorCode = GlobalErrorCode.INTERNAL_SERVER_ERROR;
        return handleExceptionInternal(errorCode);
    }

    private ResponseEntity<Object> handleExceptionInternal(final ErrorCode errorCode) {
        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(makeErrorResponse(errorCode));
    }

    private ErrorResponse makeErrorResponse(final ErrorCode errorCode) {
        return ErrorResponse.builder()
                .isSuccess(false)
                .code(errorCode.name())
                .message(errorCode.getMessage())
                .results(new ValidationErrors(null))
                .build();
    }

    private ResponseEntity<Object> handleExceptionInternal(final ErrorCode errorCode, final String message) {
        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(makeErrorResponse(errorCode, message));
    }

    private ErrorResponse makeErrorResponse(final ErrorCode errorCode, final String message) {
        return ErrorResponse.builder()
                .isSuccess(false)
                .code(errorCode.name())
                .message(message)
                .results(new ValidationErrors(null))
                .build();
    }

    private ResponseEntity<Object> handleExceptionInternal(final BindException e, final ErrorCode errorCode) {
        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(makeErrorResponse(e, errorCode));
    }

    private ErrorResponse makeErrorResponse(final BindException e, final ErrorCode errorCode) {
        final List<ValidationError> validationErrorList = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(ErrorResponse.ValidationError::from)
                .toList();

        return ErrorResponse.builder()
                .isSuccess(false)
                .code(errorCode.name())
                .message(errorCode.getMessage())
                .results(new ValidationErrors(validationErrorList))
                .build();
    }
}
