package soongsil.kidbean.server.global.application.config;

import static soongsil.kidbean.server.member.fixture.MemberFixture.MEMBER1;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import soongsil.kidbean.server.auth.application.jwt.JwtTokenProvider;
import soongsil.kidbean.server.auth.util.AuthenticationUtil;

@Import(TestSecurityConfig.class)
public class CommonControllerTest {

    @MockBean
    public JwtTokenProvider jwtTokenProvider;

    @BeforeEach
    public void setUp() {

        AuthenticationUtil.makeAuthentication(MEMBER1);
    }
}