package soongsil.kidbean.server.quizsolve.application.quizscorer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static soongsil.kidbean.server.member.fixture.MemberFixture.MEMBER1;

import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import soongsil.kidbean.server.quizsolve.application.quizsolver.dto.SolvedQuizInfo;
import soongsil.kidbean.server.quizsolve.domain.type.QuizCategory;
import soongsil.kidbean.server.summary.domain.QuizScore;
import soongsil.kidbean.server.summary.repository.QuizScoreRepository;

@ExtendWith(MockitoExtension.class)
class ImageQuizScorerTest {

    @InjectMocks
    private ImageQuizScorer imageQuizScorer;

    @Mock
    private QuizScoreRepository quizScoreRepository;

    @Test
    @DisplayName("기존 QuizScore 에 점수를 더하고 해당 점수를 반환한다")
    void addPerQuizScore_existing() {
        // given
        SolvedQuizInfo solvedQuizInfo = new SolvedQuizInfo(QuizCategory.ANIMAL, 10L);
        QuizScore quizScore = QuizScore.builder()
                .member(MEMBER1)
                .quizCategory(QuizCategory.ANIMAL)
                .totalScore(5L)
                .quizCount(1L)
                .build();
        given(quizScoreRepository.findByMemberAndQuizCategory(MEMBER1, QuizCategory.ANIMAL))
                .willReturn(Optional.of(quizScore));

        // when
        Long result = imageQuizScorer.addPerQuizScore(solvedQuizInfo, MEMBER1);

        // then
        assertThat(result).isEqualTo(10L);
        assertThat(quizScore.getTotalScore()).isEqualTo(15L);
        assertThat(quizScore.getQuizCount()).isEqualTo(2L);
    }

    @Test
    @DisplayName("QuizScore 가 없으면 IllegalArgumentException 을 던진다")
    void addPerQuizScore_notFound() {
        // given
        SolvedQuizInfo solvedQuizInfo = new SolvedQuizInfo(QuizCategory.ANIMAL, 10L);
        given(quizScoreRepository.findByMemberAndQuizCategory(MEMBER1, QuizCategory.ANIMAL))
                .willReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> imageQuizScorer.addPerQuizScore(solvedQuizInfo, MEMBER1))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
