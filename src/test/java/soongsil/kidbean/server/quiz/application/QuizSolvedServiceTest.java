package soongsil.kidbean.server.quiz.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static soongsil.kidbean.server.member.fixture.MemberFixture.MEMBER1;
import static soongsil.kidbean.server.quiz.fixture.ImageQuizFixture.IMAGE_QUIZ_ANIMAL1;
import static soongsil.kidbean.server.quiz.fixture.WordQuizFixture.WORD_QUIZ;

import java.util.Collections;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import soongsil.kidbean.server.quiz.application.quizscorer.ImageQuizScorer;
import soongsil.kidbean.server.quiz.application.quizscorer.QuizScorerFactory;
import soongsil.kidbean.server.quiz.application.quizscorer.WordQuizScorer;
import soongsil.kidbean.server.quiz.application.quizsolver.ImageQuizSolver;
import soongsil.kidbean.server.quiz.application.quizsolver.QuizSolverFactory;
import soongsil.kidbean.server.quiz.application.quizsolver.WordQuizSolver;
import soongsil.kidbean.server.quiz.application.quizsolver.dto.SolvedQuizInfo;
import soongsil.kidbean.server.quiz.application.vo.QuizType;
import soongsil.kidbean.server.quiz.domain.type.Level;
import soongsil.kidbean.server.quiz.dto.request.QuizSolvedRequest;

@ExtendWith(MockitoExtension.class)
class QuizSolvedServiceTest {

    @InjectMocks
    private QuizSolvedService quizSolvedService;

    @Mock
    private QuizSolverFactory quizSolverFactory;

    @Mock
    private ImageQuizSolver imageQuizSolver;

    @Mock
    private WordQuizSolver wordQuizSolver;

    @Mock
    private QuizScorerFactory quizScorerFactory;

    @Mock
    private ImageQuizScorer imageQuizScorer;

    @Mock
    private WordQuizScorer wordQuizScorer;

    @Test
    @DisplayName("WordQuiz 풀기")
    void solveQuizzes() {
        //given
        QuizSolvedRequest request =
                new QuizSolvedRequest(WORD_QUIZ.getQuizId(), WORD_QUIZ.getAnswer());
        SolvedQuizInfo solvedQuizInfo =
                new SolvedQuizInfo(WORD_QUIZ.getQuizCategory(), Level.getPoint(WORD_QUIZ.getLevel()), false);

        given(quizSolverFactory.getSolver(QuizType.WORD_QUIZ)).willReturn(wordQuizSolver);
        given(wordQuizSolver.solveQuiz(request, MEMBER1)).willReturn(solvedQuizInfo);
        given(quizScorerFactory.getScorer(QuizType.WORD_QUIZ)).willReturn(wordQuizScorer);
        given(wordQuizScorer.addPerQuizScore(solvedQuizInfo, MEMBER1)).willReturn(Level.getPoint(WORD_QUIZ.getLevel()));

        //when
        Long score = quizSolvedService.solveQuizzes(Collections.singletonList(request), MEMBER1, QuizType.WORD_QUIZ);

        //then
        assertThat(score).isEqualTo(Level.getPoint(WORD_QUIZ.getLevel()));
    }

    @Test
    @DisplayName("ImageQuiz 풀기")
    void solveQuizzes2() {
        //given
        QuizSolvedRequest request =
                new QuizSolvedRequest(IMAGE_QUIZ_ANIMAL1.getQuizId(), IMAGE_QUIZ_ANIMAL1.getAnswer());
        SolvedQuizInfo solvedQuizInfo =
                new SolvedQuizInfo(IMAGE_QUIZ_ANIMAL1.getQuizCategory(), Level.getPoint(IMAGE_QUIZ_ANIMAL1.getLevel()),
                        false);

        given(quizSolverFactory.getSolver(QuizType.IMAGE_QUIZ)).willReturn(imageQuizSolver);
        given(imageQuizSolver.solveQuiz(request, MEMBER1)).willReturn(solvedQuizInfo);
        given(quizScorerFactory.getScorer(QuizType.IMAGE_QUIZ)).willReturn(imageQuizScorer);
        given(imageQuizScorer.addPerQuizScore(solvedQuizInfo, MEMBER1)).willReturn(
                Level.getPoint(IMAGE_QUIZ_ANIMAL1.getLevel()));

        //when
        Long score = quizSolvedService.solveQuizzes(Collections.singletonList(request), MEMBER1, QuizType.IMAGE_QUIZ);

        //then
        assertThat(score).isEqualTo(Level.getPoint(IMAGE_QUIZ_ANIMAL1.getLevel()));
    }
}
