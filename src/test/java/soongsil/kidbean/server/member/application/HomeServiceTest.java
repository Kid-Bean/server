package soongsil.kidbean.server.member.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import soongsil.kidbean.server.member.dto.response.HomeResponse;
import soongsil.kidbean.server.member.repository.MemberRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static soongsil.kidbean.server.member.fixture.MemberFixture.MEMBER1;

@ExtendWith(MockitoExtension.class)
class HomeServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private HomeService homeService;

    @Test
    @DisplayName("홈화면 정보 가져오기")
    void getHomeInfo() {
        // given
        given(memberRepository.findById(anyLong())).willReturn(Optional.of(MEMBER1));
        
        // when
        HomeResponse response = homeService.getHomeInfo(anyLong());

        // then
        assertThat(response.name()).isEqualTo(MEMBER1.getName());
    }
}
