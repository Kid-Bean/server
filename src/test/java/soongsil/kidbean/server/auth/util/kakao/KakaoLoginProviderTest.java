package soongsil.kidbean.server.auth.util.kakao;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import soongsil.kidbean.server.auth.dto.response.KakaoUserResponse;
import soongsil.kidbean.server.auth.dto.response.KakaoUserResponse.KakaoAccount;
import soongsil.kidbean.server.member.domain.Member;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class KakaoLoginProviderTest {

    @InjectMocks
    private KakaoLoginProvider kakaoLoginProvider;

    @Mock
    private KakaoClient kakaoClient;

    private static final KakaoUserResponse mockResponse = KakaoUserResponse.builder()
            .id(12345L)
            .kakaoAccount(new KakaoAccount("test@example.com"))
            .build();

    @Test
    void getUserData() {
        //given
        String accessToken = "testToken";
        given(kakaoClient.getUserData("Bearer " + accessToken)).willReturn(mockResponse);

        //when
        Member member = kakaoLoginProvider.getUserData(accessToken);

        //then
        assertThat(member.getEmail()).isEqualTo(mockResponse.kakaoAccount().email());
        assertThat(member.getSocialId()).isEqualTo(mockResponse.id().toString());
    }
}
