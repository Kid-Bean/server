package soongsil.kidbean.server.member.exception.errorcode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import soongsil.kidbean.server.global.exception.errorcode.ErrorCode;

@Getter
@RequiredArgsConstructor
public enum MemberErrorCode implements ErrorCode {
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "Member not found"),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
