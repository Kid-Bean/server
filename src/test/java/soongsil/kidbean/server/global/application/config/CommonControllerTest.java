package soongsil.kidbean.server.global.application.config;

import static soongsil.kidbean.server.member.fixture.MemberFixture.MEMBER1;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import soongsil.kidbean.server.auth.jwt.JwtTokenProvider;
import soongsil.kidbean.server.auth.util.AuthenticationUtil;

//나중에 spring rest docs 사용시 공통 설정을 위해 미리 분리
@MockBean(JpaMetamodelMappingContext.class)
public class CommonControllerTest {

    @MockBean
    public JwtTokenProvider jwtTokenProvider;

    @BeforeEach
    public void setUp() {

        // AuthUser.getSocialId() 에서 NullPointerException 방지를 위한 Authentication 생성
        AuthenticationUtil.makeAuthentication(MEMBER1);
    }
}