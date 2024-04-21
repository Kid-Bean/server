package soongsil.kidbean.server.auth.jwt.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;
import soongsil.kidbean.server.auth.jwt.token.TokenProvider;
import soongsil.kidbean.server.auth.jwt.token.TokenResponse;

@Slf4j
@Component
@RequiredArgsConstructor
public class MyAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final TokenProvider tokenProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        // Kakao는 기본적으로 email 정보를 제공하지 않을 수 있습니다.
        // 따라서 사용자 정보에서 email 대신 id 또는 다른 속성을 사용해야 할 수도 있습니다.
        // 예를 들어, Kakao의 경우 "id"를 사용할 수 있습니다.
        String email = (String) ((Map<String, Object>) oAuth2User.getAttribute("kakao_account")).get("email");
        if (email == null) {
            // 이메일이 없는 경우 다른 속성 사용
            email = oAuth2User.getAttribute("id").toString(); // id를 이메일 대신 사용
        }

        ObjectMapper objectMapper = new ObjectMapper();
        log.info("{}", objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(oAuth2User));
        String provider = oAuth2User.getAttribute("provider");

        log.info("login succeed.");
        log.info("email: {}", email);
        log.info("provider: {}", provider);

        boolean isExist = Boolean.TRUE.equals(oAuth2User.getAttribute("exist"));

        String role = oAuth2User.getAuthorities().stream()
                .findFirst()
                .orElseThrow(IllegalAccessError::new)
                .getAuthority();

        if (isExist) {
            // JWT 토큰 생성 로직 활성화
            // 여기서 jwtUtil은 JWT 토큰을 생성하는 유틸리티 클래스의 인스턴스입니다.
            TokenResponse token = tokenProvider.createToken(email, email, role);
            log.info("jwtToken = {}", token.getAccessToken());

            String targetUrl = UriComponentsBuilder.fromUriString("http://localhost/loginSuccess")
                    .queryParam("accessToken", token.getAccessToken())
                    .build()
                    .encode(StandardCharsets.UTF_8)
                    .toUriString();
            log.info("redirect 준비");
            getRedirectStrategy().sendRedirect(request, response, targetUrl);
        } else {
            // 회원가입 리다이렉션 로직 활성화
            String targetUrl = UriComponentsBuilder.fromUriString("http://localhost/loginSuccess")
                    .queryParam("email", email)
                    .queryParam("provider", provider)
                    .build()
                    .encode(StandardCharsets.UTF_8)
                    .toUriString();
            getRedirectStrategy().sendRedirect(request, response, targetUrl);
        }
    }


}