package soongsil.kidbean.server.summary.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import soongsil.kidbean.server.summary.domain.AverageScore;
import soongsil.kidbean.server.summary.domain.type.AgeGroup;
import soongsil.kidbean.server.global.domain.S3Info;
import soongsil.kidbean.server.imagequiz.domain.ImageQuiz;
import soongsil.kidbean.server.member.domain.Member;
import soongsil.kidbean.server.member.domain.type.Gender;
import soongsil.kidbean.server.member.domain.type.Role;
import soongsil.kidbean.server.member.repository.MemberRepository;
import soongsil.kidbean.server.quizsolve.domain.QuizSolved;
import soongsil.kidbean.server.quizsolve.domain.type.Level;
import soongsil.kidbean.server.quizsolve.domain.type.QuizCategory;
import soongsil.kidbean.server.quizsolve.repository.QuizSolvedRepository;
import soongsil.kidbean.server.summary.repository.AverageScoreRepository;
import soongsil.kidbean.server.summary.repository.QuizScoreRepository;

@ExtendWith(MockitoExtension.class)
class AverageScoreSchedulerTest {

    @Mock
    private QuizSolvedRepository quizSolvedRepository;
    @Mock
    private MemberRepository memberRepository;
    @Mock
    private QuizScoreRepository quizScoreRepository;
    @Mock
    private AverageScoreRepository averageScoreRepository;

    @InjectMocks
    private AverageScoreScheduler averageScoreScheduler;

    @Test
    @DisplayName("정답률이 정수 나눗셈 버그 없이 계산되어 올바른 레벨로 갱신된다 (정답 2/3 = 66% -> SILVER)")
    void updateImageQuizLevel_accuracyIsNotIntegerTruncated() {
        // given: 레벨 BRONZE인 어드민 이미지 퀴즈에 정답 2 / 오답 1 (정답률 66%)
        ImageQuiz imageQuiz = ImageQuiz.builder()
                .quizCategory(QuizCategory.ANIMAL)
                .answer("answer")
                .title("title")
                .level(Level.BRONZE)
                .member(member(1L, Role.ADMIN))
                .isDefault(true)
                .s3Info(new S3Info("url", null, null))
                .build();
        ReflectionTestUtils.setField(imageQuiz, "quizId", 100L);

        List<QuizSolved> solvedList = List.of(
                solved(imageQuiz, true, member(10L, Role.MEMBER)),
                solved(imageQuiz, true, member(11L, Role.MEMBER)),
                solved(imageQuiz, false, member(12L, Role.MEMBER))
        );

        given(quizSolvedRepository.findAllAdminImageQuizSolved()).willReturn(solvedList);
        given(quizScoreRepository.findByMemberAndQuizCategory(any(Member.class), eq(QuizCategory.ANIMAL)))
                .willReturn(Optional.empty());

        // when
        averageScoreScheduler.updateImageQuizLevel();

        // then: (2 * 100) / 3 = 66 -> SILVER.
        // 과거 정수 나눗셈 버그였다면 (2 / 3) * 100 = 0 -> DIAMOND 로 잘못 계산됐을 것이다.
        assertThat(imageQuiz.getLevel()).isEqualTo(Level.SILVER);
    }

    @Test
    @DisplayName("레벨 변동이 없으면 점수/레벨을 갱신하지 않는다")
    void updateImageQuizLevel_noChangeWhenLevelSame() {
        // given: 정답 0 / 오답 3 -> 정답률 0% -> DIAMOND. 이미 DIAMOND이면 갱신 불필요
        ImageQuiz imageQuiz = ImageQuiz.builder()
                .quizCategory(QuizCategory.ANIMAL)
                .answer("answer")
                .title("title")
                .level(Level.DIAMOND)
                .member(member(1L, Role.ADMIN))
                .isDefault(true)
                .s3Info(new S3Info("url", null, null))
                .build();
        ReflectionTestUtils.setField(imageQuiz, "quizId", 101L);

        List<QuizSolved> solvedList = List.of(
                solved(imageQuiz, false, member(10L, Role.MEMBER)),
                solved(imageQuiz, false, member(11L, Role.MEMBER)),
                solved(imageQuiz, false, member(12L, Role.MEMBER))
        );

        given(quizSolvedRepository.findAllAdminImageQuizSolved()).willReturn(solvedList);

        // when
        averageScoreScheduler.updateImageQuizLevel();

        // then
        assertThat(imageQuiz.getLevel()).isEqualTo(Level.DIAMOND);
    }

    @Test
    @DisplayName("또래 평균 점수가 없으면 새로 저장한다 (정답 QuizSolved의 점수 합산)")
    void updateAverageScore_savesWhenAbsent() {
        // given: 3살 회원이 BRONZE(1점) 이미지 퀴즈를 맞힘
        Member member = member(10L, Role.MEMBER);
        ReflectionTestUtils.setField(member, "birthDate", LocalDate.now().minusYears(3));

        ImageQuiz imageQuiz = ImageQuiz.builder()
                .quizCategory(QuizCategory.ANIMAL)
                .answer("answer")
                .title("title")
                .level(Level.BRONZE)
                .member(member(1L, Role.ADMIN))
                .isDefault(true)
                .s3Info(new S3Info("url", null, null))
                .build();
        ReflectionTestUtils.setField(imageQuiz, "quizId", 200L);

        QuizSolved correctSolved = solved(imageQuiz, true, member);

        given(memberRepository.findAllByRole(Role.MEMBER)).willReturn(List.of(member));
        given(quizSolvedRepository.findAllCorrectByMembers(List.of(member))).willReturn(List.of(correctSolved));
        given(averageScoreRepository.findByAgeGroupAndQuizCategory(any(AgeGroup.class), any(QuizCategory.class)))
                .willReturn(Optional.empty());

        // when
        averageScoreScheduler.updateAverageScore();

        // then: ANIMAL 카테고리에 대해 AverageScore가 저장된다
        ArgumentCaptor<AverageScore> captor = ArgumentCaptor.forClass(AverageScore.class);
        verify(averageScoreRepository, atLeastOnce()).save(captor.capture());

        AverageScore animalScore = captor.getAllValues().stream()
                .filter(score -> score.getQuizCategory() == QuizCategory.ANIMAL)
                .findFirst()
                .orElseThrow();
        assertThat(animalScore.getAgeGroup()).isEqualTo(AgeGroup.THREE);
        assertThat(animalScore.getTotalScore()).isEqualTo(1L);
        assertThat(animalScore.getMemberCount()).isEqualTo(1L);
    }

    private Member member(long id, Role role) {
        Member member = Member.builder()
                .gender(Gender.MAN)
                .name("name" + id)
                .score(0L)
                .role(role)
                .socialId("social" + id)
                .email(id + "@test.com")
                .build();
        ReflectionTestUtils.setField(member, "memberId", id);
        return member;
    }

    private QuizSolved solved(ImageQuiz imageQuiz, boolean correct, Member member) {
        return QuizSolved.builder()
                .imageQuiz(imageQuiz)
                .isCorrect(correct)
                .member(member)
                .reply("reply")
                .build();
    }
}
