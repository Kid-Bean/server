package soongsil.kidbean.server.imagequiz.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import soongsil.kidbean.server.member.domain.Member;
import soongsil.kidbean.server.member.domain.type.Gender;
import soongsil.kidbean.server.member.domain.type.Role;
import soongsil.kidbean.server.member.repository.MemberRepository;

@ExtendWith(MockitoExtension.class)
class MemberScoreUpdateStrategyTest {

    @InjectMocks
    private MemberScoreUpdateStrategy memberScoreUpdateStrategy;

    @Mock
    private MemberRepository memberRepository;

    @Test
    @DisplayName("회원의 기존 점수에 전달된 점수를 더해 갱신한다")
    void updateUserScore() {
        // given
        Member member = Member.builder()
                .gender(Gender.MAN)
                .name("tester")
                .score(100L)
                .role(Role.MEMBER)
                .socialId("social")
                .email("test@test.com")
                .build();
        given(memberRepository.getByIdOrThrow(1L)).willReturn(member);

        // when
        memberScoreUpdateStrategy.updateUserScore(1L, 30L);

        // then
        assertThat(member.getScore()).isEqualTo(130L);
    }
}
