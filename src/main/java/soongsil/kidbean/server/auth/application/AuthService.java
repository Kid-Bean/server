package soongsil.kidbean.server.auth.application;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soongsil.kidbean.server.auth.dto.request.LoginRequest;
import soongsil.kidbean.server.auth.dto.response.LoginResponse;
import soongsil.kidbean.server.auth.jwt.JwtTokenProvider;
import soongsil.kidbean.server.auth.util.AuthenticationUtil;
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

    public LoginResponse signIn(LoginRequest loginRequest, String provider) {
        //1. 사용자 정보 가져오기
        Member member = getUserDataFromPlatform(loginRequest.accessToken(), provider);
        //2. 로그인 및 회원가입
        Member authenticatedMember = memberRepository.findBySocialId(member.getSocialId())
                .orElseGet(() -> memberRepository.save(member));
        //3. security 처리
        AuthenticationUtil.makeAuthentication(authenticatedMember);
        //4. token 만들기
        String accessToken = jwtTokenProvider.createAccessToken(member);
        String refreshToken = jwtTokenProvider.createRefreshToken(member);

        return LoginResponse.of(accessToken, refreshToken);
    }

    private Member getUserDataFromPlatform(String accessToken, String providerInfo) {
        SocialLoginProvider signInProvider = loginProviders.get(providerInfo + "LoginProvider");
        if (signInProvider == null) {
            throw new IllegalArgumentException("Unknown provider: " + providerInfo);
        }
        return signInProvider.getUserData(accessToken);
    }
}
