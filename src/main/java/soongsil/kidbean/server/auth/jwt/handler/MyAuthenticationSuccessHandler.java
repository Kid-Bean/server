package soongsil.kidbean.server.auth.jwt.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;
import soongsil.kidbean.server.auth.jwt.common.CustomOAuth2User;
import soongsil.kidbean.server.auth.jwt.token.JwtTokenProvider;
import soongsil.kidbean.server.member.domain.type.Role;

@Slf4j
@Component
@RequiredArgsConstructor
public class MyAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtTokenProvider jwtTokenProvider;
    private final String ACCESS_HEADER = "Authorization";

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        log.info("OAuth2 Login 성공!");
        try {
            CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();

            // User의 Role이 GUEST일 경우 처음 요청한 회원이므로 회원가입 페이지로 리다이렉트
            if (oAuth2User.getRole() == Role.GUEST) {
                String accessToken = jwtTokenProvider.createAccessToken(authentication);
                response.addHeader(ACCESS_HEADER, "Bearer " + accessToken);

                //아래에서 http://localhost:8080/auth/loginSuccess로 바꾸면 AuthController에서 받음
                String targetUrl = UriComponentsBuilder.fromUriString("http://localhost:8080/auth/loginSuccess2")
                        .queryParam("status", "ok")
                        .queryParam("accesstoken", accessToken)
                        .build()
                        .encode(StandardCharsets.UTF_8)
                        .toUriString();

                getRedirectStrategy().sendRedirect(request, response, targetUrl);
            } else {
//                loginSuccess(response, oAuth2User); // 로그인에 성공한 경우 access, refresh 토큰 생성
            }
        } catch (Exception e) {
            throw e;
        }
    }

//    //소셜 로그인 시에도 무조건 토큰 생성하지 말고 JWT 인증 필터처럼 RefreshToken 유/무에 따라 다르게 처리해보기
//    private void loginSuccess(HttpServletResponse response, CustomOAuth2User oAuth2User) {
//        String accessToken = jwtTokenProvider.createAccessToken(oAuth2User.getEmail());
//        String refreshToken = jwtTokenProvider.createRefreshToken();
//        response.addHeader(ACCESS_HEADER, "Bearer " + accessToken);
//
////        jwtTokenProvider.updateRefreshToken(oAuth2User.getEmail(), refreshToken);
//    }
}