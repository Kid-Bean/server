package soongsil.kidbean.server.quiz.application.quizsolver;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static soongsil.kidbean.server.member.fixture.MemberFixture.MEMBER1;
import static soongsil.kidbean.server.quiz.fixture.WordQuizFixture.WORD_QUIZ;

import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import soongsil.kidbean.server.quiz.application.quizsolver.dto.SolvedQuizInfo;
import soongsil.kidbean.server.quiz.domain.type.Level;
import soongsil.kidbean.server.quiz.dto.request.QuizSolvedRequest;
import soongsil.kidbean.server.quiz.repository.QuizSolvedRepository;
import soongsil.kidbean.server.quiz.repository.WordQuizRepository;

@ExtendWith(MockitoExtension.class)
class WordQuizSolverTest {

    @InjectMocks
    private WordQuizSolver wordQuizSolver;

    @Mock
    private QuizSolvedRepository quizSolvedRepository;

    @Mock
    private WordQuizRepository wordQuizRepository;

    @Test
    @DisplayName("이미 푼 맞은 WordQuiz 풀었을 때")
    public void solveWordQuiz1() {
        //given
        QuizSolvedRequest request =
                new QuizSolvedRequest(WORD_QUIZ.getQuizId(), WORD_QUIZ.getAnswer());

        given(wordQuizRepository.findById(WORD_QUIZ.getQuizId())).willReturn(Optional.of(WORD_QUIZ));
        //이전에 풀었던 문제
        given(quizSolvedRepository.existsByWordQuizAndMember(WORD_QUIZ, MEMBER1)).willReturn(true);
        given(quizSolvedRepository.existsByWordQuizAndMemberAndIsCorrect(WORD_QUIZ, MEMBER1, true))
                .willReturn(true);

        //when
        SolvedQuizInfo solvedQuizInfo = wordQuizSolver.solveQuiz(request, MEMBER1);

        //then
        assertThat(solvedQuizInfo.score()).isEqualTo(0L);
        assertThat(solvedQuizInfo.category()).isEqualTo(WORD_QUIZ.getQuizCategory());
    }

    @Test
    @DisplayName("이미 푼 틀린 WordQuiz 풀었을 때")
    public void solveWordQuiz2() {
        //given
        QuizSolvedRequest request =
                new QuizSolvedRequest(WORD_QUIZ.getQuizId(), WORD_QUIZ.getAnswer());

        given(wordQuizRepository.findById(WORD_QUIZ.getQuizId())).willReturn(Optional.of(WORD_QUIZ));
        given(quizSolvedRepository.existsByWordQuizAndMember(WORD_QUIZ, MEMBER1)).willReturn(true);
        given(quizSolvedRepository.existsByWordQuizAndMemberAndIsCorrect(WORD_QUIZ, MEMBER1, true))
                .willReturn(false);

        //when
        SolvedQuizInfo solvedQuizInfo = wordQuizSolver.solveQuiz(request, MEMBER1);

        //then
        assertThat(solvedQuizInfo.score()).isEqualTo(Level.getPoint(WORD_QUIZ.getLevel()));
        assertThat(solvedQuizInfo.category()).isEqualTo(WORD_QUIZ.getQuizCategory());
    }

    @Test
    @DisplayName("풀지 않은 WordQuiz 풀었을 때")
    public void solveWordQuiz() {
        //given
        QuizSolvedRequest request =
                new QuizSolvedRequest(WORD_QUIZ.getQuizId(), WORD_QUIZ.getAnswer());

        given(wordQuizRepository.findById(WORD_QUIZ.getQuizId())).willReturn(Optional.of(WORD_QUIZ));
        given(quizSolvedRepository.existsByWordQuizAndMember(WORD_QUIZ, MEMBER1)).willReturn(false);

        //when
        SolvedQuizInfo solvedQuizInfo = wordQuizSolver.solveQuiz(request, MEMBER1);

        //then
        assertThat(solvedQuizInfo.score()).isEqualTo(Level.getPoint(WORD_QUIZ.getLevel()));
        assertThat(solvedQuizInfo.category()).isEqualTo(WORD_QUIZ.getQuizCategory());
    }
}