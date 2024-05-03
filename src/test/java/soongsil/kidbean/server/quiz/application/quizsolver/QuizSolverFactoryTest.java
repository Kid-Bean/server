package soongsil.kidbean.server.quiz.application.quizsolver;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import soongsil.kidbean.server.quiz.application.vo.QuizType;

@ExtendWith(MockitoExtension.class)
class QuizSolverFactoryTest {

    @InjectMocks
    private QuizSolverFactory quizSolverFactory;

    @Mock
    private ImageQuizSolver imageQuizSolver;

    @Mock
    private WordQuizSolver wordQuizSolver;

    @Test
    @DisplayName("ImageQuizSolver 테스트")
    void getImageQuizSolver() {
        //given

        //when
        QuizSolver solver = quizSolverFactory.getSolver(QuizType.IMAGE_QUIZ);

        //then
        Assertions.assertThat(solver).isInstanceOf(ImageQuizSolver.class);
    }

    @Test
    @DisplayName("WordQuizSolver 테스트")
    void getWordQuizSolver() {
        //given

        //when
        QuizSolver solver = quizSolverFactory.getSolver(QuizType.WORD_QUIZ);

        //then
        Assertions.assertThat(solver).isInstanceOf(WordQuizSolver.class);
    }
}