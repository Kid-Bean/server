package soongsil.kidbean.server.auth.presentation;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import soongsil.kidbean.server.auth.application.AuthService;
import soongsil.kidbean.server.auth.jwt.token.LoginResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final OAuth2AuthorizedClientService authorizedClientService;

    //코드 이용 access token 발급 - 안드로이드에서 code 넘겨주기
    @PostMapping("/login/kakao")
    public ResponseEntity<LoginResponse> loginKakao(@RequestParam(name = "code") String code)
            throws JsonProcessingException {
        LoginResponse loginResponse = authService.kakaoLogin(code);
        return new ResponseEntity<>(loginResponse, HttpStatus.OK);
    }

    @GetMapping("/loginSuccess")
    public String loginSuccess(@AuthenticationPrincipal OAuth2User oauth2User) {
        // OAuth2AuthorizedClient를 통해 access token에 접근
        OAuth2AuthorizedClient authorizedClient = this.authorizedClientService.loadAuthorizedClient(
                "kakao", oauth2User.getName());
        String accessToken = authorizedClient.getAccessToken().getTokenValue();

        // 필요한 로직 추가
        return "로그인 성공: Access Token = " + accessToken;
    }
}