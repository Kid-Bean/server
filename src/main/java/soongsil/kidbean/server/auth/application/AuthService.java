package soongsil.kidbean.server.auth.application;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soongsil.kidbean.server.auth.dto.request.LoginRequest;
import soongsil.kidbean.server.auth.dto.request.ReissueRequest;
import soongsil.kidbean.server.auth.dto.response.LoginResponse;
import soongsil.kidbean.server.auth.dto.response.ReissueResponse;
import soongsil.kidbean.server.auth.exception.TokenNotValidException;
import soongsil.kidbean.server.auth.exception.errorcode.AuthErrorCode;
import soongsil.kidbean.server.auth.jwt.JwtTokenProvider;
import soongsil.kidbean.server.auth.util.SocialLoginProvider;
import soongsil.kidbean.server.member.domain.Member;
import soongsil.kidbean.server.member.repository.MemberRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {

    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;
    private final Map<String, SocialLoginProvider> loginProviders;

    /**
     * 유저가 로그인 or 회원 가입 - social 로그인 access token 과 refresh 토큰을 발급
     */
    public LoginResponse signIn(LoginRequest loginRequest, String provider) {

        //사용자 정보 가져오기
        Member member = getUserDataFromPlatform(loginRequest.accessToken(), provider);

        //로그인 및 회원가입
        Member authenticatedMember = memberRepository.findBySocialId(member.getSocialId())
                .orElseGet(() -> memberRepository.save(member));

        //token 만들기
        String accessToken = jwtTokenProvider.createAccessToken(authenticatedMember);
        String refreshToken = jwtTokenProvider.createRefreshToken(authenticatedMember);

        return LoginResponse.of(accessToken, refreshToken);
    }

    public ReissueResponse reissueAccessToken(ReissueRequest reissueRequest) {

        String refreshToken = reissueRequest.refreshToken();

        if (jwtTokenProvider.validateToken(refreshToken)) {
            Member member = jwtTokenProvider.getMember(refreshToken);
            String accessToken = jwtTokenProvider.createAccessToken(member);

            return ReissueResponse.from(accessToken);
        } else {
            throw new TokenNotValidException(AuthErrorCode.TOKEN_NOT_VALID);
        }
    }

    private Member getUserDataFromPlatform(String accessToken, String providerInfo) {
        SocialLoginProvider signInProvider = loginProviders.get(providerInfo + "LoginProvider");
        if (signInProvider == null) {
            throw new IllegalArgumentException("Unknown provider: " + providerInfo);
        }
        return signInProvider.getUserData(accessToken);
    }
}
