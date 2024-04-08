package soongsil.kidbean.server.member.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import soongsil.kidbean.server.global.exception.errorcode.ErrorCode;

@Getter
@RequiredArgsConstructor
public class MemberNotFoundException extends RuntimeException {

    private final ErrorCode errorCode;
}
