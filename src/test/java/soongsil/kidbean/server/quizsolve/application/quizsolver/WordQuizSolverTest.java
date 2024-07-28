package soongsil.kidbean.server.quizsolve.application.quizsolver;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static soongsil.kidbean.server.member.fixture.MemberFixture.MEMBER1;
import static soongsil.kidbean.server.quizsolve.fixture.QuizSolvedFixture.WORD_QUIZ_SOLVED_TRUE;
import static soongsil.kidbean.server.wordquiz.fixture.WordQuizFixture.WORD_QUIZ;

import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import soongsil.kidbean.server.quizsolve.application.quizsolver.dto.SolvedQuizInfo;
import soongsil.kidbean.server.quizsolve.domain.type.Level;
import soongsil.kidbean.server.quizsolve.dto.request.QuizSolvedRequest;
import soongsil.kidbean.server.quizsolve.repository.QuizSolvedRepository;
import soongsil.kidbean.server.wordquiz.repository.WordQuizRepository;

@ExtendWith(MockitoExtension.class)
class WordQuizSolverTest {

    @InjectMocks
    private WordQuizSolver wordQuizSolver;

    @Mock
    private QuizSolvedRepository quizSolvedRepository;

    @Mock
    private WordQuizRepository wordQuizRepository;

    @Test
    @DisplayName("풀지 않은 WordQuiz 풀었을 때")
    public void solveWordQuiz() {
        //given
        QuizSolvedRequest request =
                new QuizSolvedRequest(WORD_QUIZ.getQuizId(), WORD_QUIZ.getAnswer());

        given(wordQuizRepository.findById(WORD_QUIZ.getQuizId())).willReturn(Optional.of(WORD_QUIZ));
        given(quizSolvedRepository.save(WORD_QUIZ_SOLVED_TRUE)).willReturn(WORD_QUIZ_SOLVED_TRUE);

        //when
        SolvedQuizInfo solvedQuizInfo = wordQuizSolver.solveQuiz(request, MEMBER1);

        //then
        assertThat(solvedQuizInfo.score()).isEqualTo(Level.getPoint(WORD_QUIZ.getLevel()));
        assertThat(solvedQuizInfo.category()).isEqualTo(WORD_QUIZ.getQuizCategory());
    }
}