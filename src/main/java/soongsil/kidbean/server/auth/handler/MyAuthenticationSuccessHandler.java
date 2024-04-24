package soongsil.kidbean.server.auth.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import soongsil.kidbean.server.member.domain.type.OAuthType;
import soongsil.kidbean.server.auth.jwt.JwtTokenProvider;
import soongsil.kidbean.server.global.dto.ResponseTemplate;
import soongsil.kidbean.server.member.domain.Member;
import soongsil.kidbean.server.member.domain.type.Gender;
import soongsil.kidbean.server.member.domain.type.Role;
import soongsil.kidbean.server.member.repository.MemberRepository;

@Slf4j
@Component
@RequiredArgsConstructor
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtTokenProvider jwtTokenProvider;
    private final ObjectMapper objectMapper;
    private final MemberRepository memberRepository;
    private final String ACCESS_HEADER = "Authorization";

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        log.info("OAuth2 Login 성공!");
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        Long socialId = (Long) oAuth2User.getAttributes().get("id");
        String email = (String) oAuth2User.getAttributes().get("email");

        Member member = memberRepository.findBySocialId(socialId.toString())
                .orElseGet(() -> Member.builder()
                        .email(email)
                        .role(Role.GUEST)
                        .gender(Gender.NONE)
                        .socialId(socialId.toString())
                        .oAuthType(OAuthType.KAKAO)
                        .score(0L)
                        .build());

        log.info("oAuth2User: {}", objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(oAuth2User));

        // User의 Role이 GUEST일 경우 처음 요청한 회원이므로 회원가입 페이지로 리다이렉트
        if (member.getRole() == Role.GUEST) {
            String accessToken = jwtTokenProvider.createAccessToken(member, authentication);
            response.addHeader(ACCESS_HEADER, "Bearer " + accessToken);

            log.info("token: {}", accessToken);

            // Response message 생성
            writeOauthResponse(response);
        } else {
            //유저가 처음 등록하지 않은 경우 - 토큰만 재발급 or 아무것도 안하기
        }
    }

    private void writeOauthResponse(HttpServletResponse response) throws IOException {

        response.setContentType("application/json;charset=UTF-8");

        // Httpbody에 json 형태로 로그인 내용 추가
        var writer = response.getWriter();
        writer.println(objectMapper.writeValueAsString(ResponseTemplate.EMPTY_RESPONSE));
        writer.flush();
    }
}