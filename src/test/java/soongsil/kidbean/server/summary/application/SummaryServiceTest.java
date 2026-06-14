package soongsil.kidbean.server.summary.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import soongsil.kidbean.server.member.domain.Member;
import soongsil.kidbean.server.member.domain.type.Gender;
import soongsil.kidbean.server.member.domain.type.Role;
import soongsil.kidbean.server.member.dto.response.ImageQuizScoreResponse;
import soongsil.kidbean.server.member.repository.MemberRepository;
import soongsil.kidbean.server.quizsolve.domain.type.QuizCategory;
import soongsil.kidbean.server.summary.domain.AverageScore;
import soongsil.kidbean.server.summary.domain.QuizScore;
import soongsil.kidbean.server.summary.domain.type.AgeGroup;
import soongsil.kidbean.server.summary.repository.AverageScoreRepository;
import soongsil.kidbean.server.summary.repository.QuizScoreRepository;

@ExtendWith(MockitoExtension.class)
class SummaryServiceTest {

    @Mock
    private QuizScoreRepository imageQuizScoreRepository;
    @Mock
    private AverageScoreRepository averageScoreRepository;
    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private SummaryService summaryService;

    private Member member(LocalDate birthDate) {
        Member member = Member.builder()
                .gender(Gender.MAN)
                .name("name")
                .score(0L)
                .role(Role.MEMBER)
                .socialId("social")
                .email("test@test.com")
                .birthDate(birthDate)
                .build();
        ReflectionTestUtils.setField(member, "memberId", 1L);
        return member;
    }

    @Test
    @DisplayName("생일이 있으면 내 점수와 같은 또래 평균 점수를 함께 반환한다")
    void findImageQuizScore_withBirthDate() {
        // given
        Member member = member(LocalDate.now().minusYears(3));
        QuizScore quizScore = new QuizScore(member, QuizCategory.ANIMAL, 10L, 4L);
        AverageScore averageScore = AverageScore.builder()
                .ageGroup(AgeGroup.THREE)
                .sum(20L)
                .memberCount(5L)
                .category(QuizCategory.ANIMAL)
                .build();

        given(memberRepository.getByIdOrThrow(member.getMemberId())).willReturn(member);
        given(imageQuizScoreRepository.findAllByMember(member)).willReturn(List.of(quizScore));
        given(averageScoreRepository.findAllByAgeGroup(AgeGroup.THREE)).willReturn(List.of(averageScore));

        // when
        ImageQuizScoreResponse response = summaryService.findImageQuizScore(member.getMemberId());

        // then
        assertThat(response.myScoreInfo()).hasSize(1);
        assertThat(response.myScoreInfo().get(0).score()).isEqualTo(10L);
        assertThat(response.ageScoreInfo()).hasSize(1);
        assertThat(response.ageScoreInfo().get(0).score()).isEqualTo(20L);
    }

    @Test
    @DisplayName("생일이 없으면 또래 평균 점수 조회 없이 빈 목록을 반환한다")
    void findImageQuizScore_withoutBirthDate() {
        // given
        Member member = member(null);
        QuizScore quizScore = new QuizScore(member, QuizCategory.ANIMAL, 10L, 4L);

        given(memberRepository.getByIdOrThrow(member.getMemberId())).willReturn(member);
        given(imageQuizScoreRepository.findAllByMember(member)).willReturn(List.of(quizScore));

        // when
        ImageQuizScoreResponse response = summaryService.findImageQuizScore(member.getMemberId());

        // then
        assertThat(response.myScoreInfo()).hasSize(1);
        assertThat(response.ageScoreInfo()).isEmpty();
        verify(averageScoreRepository, never()).findAllByAgeGroup(any());
    }
}
