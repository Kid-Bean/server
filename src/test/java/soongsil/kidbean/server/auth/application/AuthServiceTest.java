package soongsil.kidbean.server.auth.application;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import soongsil.kidbean.server.auth.dto.request.LoginRequest;
import soongsil.kidbean.server.auth.dto.request.ReissueRequest;
import soongsil.kidbean.server.auth.jwt.JwtTokenProvider;
import soongsil.kidbean.server.auth.util.SocialLoginProvider;
import soongsil.kidbean.server.auth.util.kakao.KakaoLoginProvider;
import soongsil.kidbean.server.member.domain.Member;
import soongsil.kidbean.server.member.repository.MemberRepository;

import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static soongsil.kidbean.server.member.fixture.MemberFixture.MEMBER;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private Map<String, SocialLoginProvider> loginProviders;

    @Mock
    private KakaoLoginProvider kakaoLoginProvider;

    @InjectMocks
    private AuthService authService;

    private static final String accessToken = "access-token";
    private static final String refreshToken = "refresh-token";
    private static final String provider = "kakao";

    @Test
    void signInTest() {
        //given
        LoginRequest loginRequest = new LoginRequest(accessToken);

        given(loginProviders.get(anyString())).willReturn(kakaoLoginProvider);
        given(kakaoLoginProvider.getUserData(eq(accessToken))).willReturn(MEMBER);
        given(memberRepository.findBySocialId(anyString())).willReturn(Optional.of(MEMBER));
        given(jwtTokenProvider.createAccessToken(any(Member.class))).willReturn(accessToken);
        given(jwtTokenProvider.createRefreshToken(any(Member.class))).willReturn(refreshToken);

        //when
        var response = authService.signIn(loginRequest, provider);

        //then
        assertThat(response.accessToken()).isEqualTo(accessToken);
        assertThat(response.refreshToken()).isEqualTo(refreshToken);
    }

    @Test
    void reissueAccessTokenTest() {
        // Given
        String validRefreshToken = "valid-refresh-token";
        String newAccessToken = "new-access-token";

        ReissueRequest reissueRequest = new ReissueRequest(validRefreshToken);

        given(jwtTokenProvider.validateToken(eq(validRefreshToken))).willReturn(true);
        given(jwtTokenProvider.getMember(eq(validRefreshToken))).willReturn(MEMBER);
        given(jwtTokenProvider.createAccessToken(eq(MEMBER))).willReturn(newAccessToken);

        // When
        var response = authService.reissueAccessToken(reissueRequest);

        // Then
        assertThat(response.accessToken()).isEqualTo(newAccessToken);
    }
}