package soongsil.kidbean.server.auth.application.jwt;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static soongsil.kidbean.server.member.fixture.MemberFixture.MEMBER1;

import io.jsonwebtoken.io.Encoders;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import soongsil.kidbean.server.member.domain.Member;
import soongsil.kidbean.server.member.repository.MemberRepository;

@ExtendWith(MockitoExtension.class)
class JwtTokenProviderTest {

    @Mock
    private MemberRepository memberRepository;

    private JwtTokenProvider jwtTokenProvider;

    @BeforeEach
    void setUp() {
        // HS512 서명에는 최소 512비트(64바이트) 키가 필요하므로 충분한 길이의 키를 생성한다.
        String secretKey = Encoders.BASE64.encode("0123456789".repeat(7).getBytes(StandardCharsets.UTF_8));
        JwtProperties jwtProperties = new JwtProperties(secretKey, "kidbean", 3_600_000L, 7_200_000L);

        jwtTokenProvider = new JwtTokenProvider(jwtProperties, memberRepository);
        jwtTokenProvider.setKey(); // @PostConstruct를 수동 호출 (파서 캐싱 포함)
    }

    @Test
    @DisplayName("발급한 AccessToken은 유효성 검증을 통과한다")
    void createAndValidateAccessToken() {
        String token = jwtTokenProvider.createAccessToken(MEMBER1);

        assertThat(jwtTokenProvider.validateToken(token)).isTrue();
    }

    @Test
    @DisplayName("잘못된 토큰은 유효성 검증을 통과하지 못한다")
    void validateInvalidToken() {
        assertThat(jwtTokenProvider.validateToken("invalid.token.value")).isFalse();
    }

    @Test
    @DisplayName("토큰의 subject로 회원을 조회한다")
    void getMemberFromToken() {
        given(memberRepository.getByIdOrThrow(MEMBER1.getMemberId())).willReturn(MEMBER1);
        String token = jwtTokenProvider.createAccessToken(MEMBER1);

        Member member = jwtTokenProvider.getMember(token);

        assertThat(member).isEqualTo(MEMBER1);
    }
}
