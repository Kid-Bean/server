package soongsil.kidbean.server.member.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import soongsil.kidbean.server.member.dto.request.MemberInfoRequest;
import soongsil.kidbean.server.member.repository.MemberRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static soongsil.kidbean.server.member.fixture.MemberFixture.MEMBER1;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private MemberService memberService;

    @Test
    @DisplayName("아이 정보 입력하기")
    void uploadMemberInfo() {
        // given
        MemberInfoRequest request = new MemberInfoRequest(MEMBER1.getName(), MEMBER1.getGender(), MEMBER1.getBirthDate());

        given(memberRepository.findById(anyLong())).willReturn(Optional.of(MEMBER1));

        // when
        memberService.uploadMemberInfo(request, anyLong());

        // then
        assertThat(request.name()).isEqualTo(MEMBER1.getName());
    }
}
