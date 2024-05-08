package soongsil.kidbean.server.auth.util.kakao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import lombok.SneakyThrows;
import org.apache.http.HttpHeaders;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.http.MediaType;
import soongsil.kidbean.server.auth.dto.response.KakaoUserResponse;
import soongsil.kidbean.server.auth.dto.response.KakaoUserResponse.KakaoAccount;
import soongsil.kidbean.server.auth.jwt.JwtTokenProvider;
import soongsil.kidbean.server.auth.presentation.AuthController;
import soongsil.kidbean.server.global.application.S3Uploader;
import soongsil.kidbean.server.member.domain.Member;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@EnableFeignClients(clients = KakaoClient.class)
public class KakaoLoginProviderTest {

    @Autowired
    private KakaoLoginProvider kakaoLoginProvider;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private ObjectMapper objectMapper;

    //나중에 뺄 수 있는 방법 찾아보기
    @MockBean
    S3Uploader s3Uploader;

    @MockBean
    private AuthController authController;

    private WireMockServer wireMockServer;

    private static final KakaoUserResponse mockResponse = KakaoUserResponse.builder()
            .id(12345L)
            .kakaoAccount(new KakaoAccount("test@example.com"))
            .build();

    @SneakyThrows
    @BeforeEach
    void setUp() {
        wireMockServer = new WireMockServer(12356);
        wireMockServer.start();
        WireMock.configureFor("localhost", wireMockServer.port());

        wireMockServer.stubFor(get("/v2/user/me")
                .willReturn(aResponse()
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBody(objectMapper.writeValueAsString(mockResponse))));
    }

    @AfterEach
    void tearDown() {
        wireMockServer.stop();
    }

    @Test
    void getUserData() {
        //given
        String accessToken = "testToken";

        //when
        Member member = kakaoLoginProvider.getUserData(accessToken);

        //then
        assertThat(member.getEmail()).isEqualTo(mockResponse.kakaoAccount().email());
        assertThat(member.getSocialId()).isEqualTo(mockResponse.id().toString());
    }
}
