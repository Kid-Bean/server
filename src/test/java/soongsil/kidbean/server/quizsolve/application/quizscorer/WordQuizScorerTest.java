package soongsil.kidbean.server.quizsolve.application.quizscorer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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
class WordQuizScorerTest {

    @InjectMocks
    private WordQuizScorer wordQuizScorer;

    @Mock
    private QuizScoreRepository quizScoreRepository;

    @Test
    @DisplayName("기존 QuizScore 가 있으면 점수를 더하고 저장하지 않는다")
    void addPerQuizScore_existing() {
        // given
        SolvedQuizInfo solvedQuizInfo = new SolvedQuizInfo(QuizCategory.FOOD, 7L);
        QuizScore quizScore = QuizScore.builder()
                .member(MEMBER1)
                .quizCategory(QuizCategory.FOOD)
                .totalScore(3L)
                .quizCount(1L)
                .build();
        given(quizScoreRepository.findByMemberAndQuizCategory(MEMBER1, QuizCategory.FOOD))
                .willReturn(Optional.of(quizScore));

        // when
        Long result = wordQuizScorer.addPerQuizScore(solvedQuizInfo, MEMBER1);

        // then
        assertThat(result).isEqualTo(7L);
        assertThat(quizScore.getTotalScore()).isEqualTo(10L);
        verify(quizScoreRepository, never()).save(any(QuizScore.class));
    }

    @Test
    @DisplayName("QuizScore 가 없으면 새로 저장한 뒤 점수를 더한다")
    void addPerQuizScore_notFound() {
        // given
        SolvedQuizInfo solvedQuizInfo = new SolvedQuizInfo(QuizCategory.FOOD, 7L);
        QuizScore initScore = QuizScore.makeInitQuizScore(MEMBER1, QuizCategory.FOOD);
        given(quizScoreRepository.findByMemberAndQuizCategory(MEMBER1, QuizCategory.FOOD))
                .willReturn(Optional.empty());
        given(quizScoreRepository.save(any(QuizScore.class))).willReturn(initScore);

        // when
        Long result = wordQuizScorer.addPerQuizScore(solvedQuizInfo, MEMBER1);

        // then
        assertThat(result).isEqualTo(7L);
        assertThat(initScore.getTotalScore()).isEqualTo(7L);
        verify(quizScoreRepository, times(1)).save(any(QuizScore.class));
    }
}
