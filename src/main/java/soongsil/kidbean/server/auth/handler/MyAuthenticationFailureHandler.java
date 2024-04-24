package soongsil.kidbean.server.auth.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import soongsil.kidbean.server.auth.exception.errorcode.OAuthErrorCode;
import soongsil.kidbean.server.global.exception.errorcode.ErrorCode;
import soongsil.kidbean.server.global.exception.response.ErrorResponse;

@Slf4j
@Component
@RequiredArgsConstructor
public class MyAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException e) throws IOException {
        log.error("[" + e.getClass().getSimpleName() + "] " + e.getMessage());

        setErrorResponse(response, OAuthErrorCode.LOGIN_TYPE_NOT_SUPPORT);
    }

    private void setErrorResponse(HttpServletResponse response, ErrorCode errorCode) throws IOException {
        response.setStatus(500);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        //임시로 에러 메시지 작성
        objectMapper.writeValue(response.getWriter(), new ErrorResponse(false, "404", errorCode.getMessage(), null));
    }
}