package soongsil.kidbean.server.auth.presentation;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import soongsil.kidbean.server.auth.application.AuthService;
import soongsil.kidbean.server.auth.dto.request.LoginRequest;
import soongsil.kidbean.server.auth.dto.request.ReissueRequest;
import soongsil.kidbean.server.auth.dto.response.LoginResponse;
import soongsil.kidbean.server.auth.dto.response.ReissueResponse;
import soongsil.kidbean.server.auth.application.jwt.JwtTokenProvider;
import soongsil.kidbean.server.global.dto.ResponseTemplate;
import soongsil.kidbean.server.member.repository.MemberRepository;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    @Value("${server.env}")
    private String env;

    private final AuthService authService;
    //이 아래는 테스트 용도 - 나중에 삭제
    private final JwtTokenProvider tokenProvider;
    private final MemberRepository memberRepository;

    @Operation(summary = "소셜 로그인 진행", description = "소셜 로그인 진행")
    @PostMapping("/login/{provider}")
    public ResponseEntity<ResponseTemplate<Object>> socialLogin(@PathVariable String provider,
                                                                @RequestBody LoginRequest request) {
        LoginResponse loginResponse = authService.signIn(request, provider);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.from(loginResponse));
    }

    @Operation(summary = "access token 재발급", description = "access token 재발급")
    @PostMapping("/reissue")
    public ResponseEntity<ResponseTemplate<Object>> reissue(@RequestBody ReissueRequest request) {
        ReissueResponse reissueResponse = authService.reissueAccessToken(request);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.from(reissueResponse));
    }

    @Operation(summary = "테스트 용 access token 발급", description = "테스트 용 access token 발급")
    @GetMapping("test/login/{memberId}")
    public ResponseEntity<String> testAccessToken(@PathVariable Long memberId) {
        log.info("server: {}", env);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(tokenProvider.createAccessToken(memberRepository.findById(memberId).orElseThrow()));
    }

    /**
     * 배포 환경 테스트
     */
    @GetMapping("/deploy/status")
    public ResponseEntity<Object> getEnv() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(env);
    }
}