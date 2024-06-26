package soongsil.kidbean.server.quizsolve.application.quizsolver;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static soongsil.kidbean.server.member.fixture.MemberFixture.MEMBER1;
import static soongsil.kidbean.server.imagequiz.fixture.ImageQuizFixture.IMAGE_QUIZ_ANIMAL1;

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
import soongsil.kidbean.server.imagequiz.repository.ImageQuizRepository;
import soongsil.kidbean.server.quizsolve.repository.QuizSolvedRepository;

@ExtendWith(MockitoExtension.class)
class ImageQuizSolverTest {

    @InjectMocks
    private ImageQuizSolver imageQuizSolver;

    @Mock
    private QuizSolvedRepository quizSolvedRepository;

    @Mock
    private ImageQuizRepository imageQuizRepository;

    @Test
    @DisplayName("이미 푼 맞은 ImageQuiz 풀었을 때")
    public void solveImageQuiz1() {
        //given
        QuizSolvedRequest request =
                new QuizSolvedRequest(IMAGE_QUIZ_ANIMAL1.getQuizId(), IMAGE_QUIZ_ANIMAL1.getAnswer());

        given(imageQuizRepository.findById(IMAGE_QUIZ_ANIMAL1.getQuizId()))
                .willReturn(Optional.of(IMAGE_QUIZ_ANIMAL1));
        //이전에 풀었던 문제
        given(quizSolvedRepository.existsByImageQuizAndMember(IMAGE_QUIZ_ANIMAL1, MEMBER1)).willReturn(true);
        given(quizSolvedRepository.existsByImageQuizAndMemberAndIsCorrect(IMAGE_QUIZ_ANIMAL1, MEMBER1, true))
                .willReturn(true);

        //when
        SolvedQuizInfo solvedQuizInfo = imageQuizSolver.solveQuiz(request, MEMBER1);

        //then
        assertThat(solvedQuizInfo.score()).isEqualTo(0L);
        assertThat(solvedQuizInfo.category()).isEqualTo(IMAGE_QUIZ_ANIMAL1.getQuizCategory());
    }

    @Test
    @DisplayName("이미 푼 틀린 ImageQuiz 풀었을 때")
    public void solveImageQuiz2() {
        //given
        QuizSolvedRequest request =
                new QuizSolvedRequest(IMAGE_QUIZ_ANIMAL1.getQuizId(), IMAGE_QUIZ_ANIMAL1.getAnswer());

        given(imageQuizRepository.findById(IMAGE_QUIZ_ANIMAL1.getQuizId())).willReturn(Optional.of(IMAGE_QUIZ_ANIMAL1));
        given(quizSolvedRepository.existsByImageQuizAndMember(IMAGE_QUIZ_ANIMAL1, MEMBER1)).willReturn(true);
        given(quizSolvedRepository.existsByImageQuizAndMemberAndIsCorrect(IMAGE_QUIZ_ANIMAL1, MEMBER1, true))
                .willReturn(false);

        //when
        SolvedQuizInfo solvedQuizInfo = imageQuizSolver.solveQuiz(request, MEMBER1);

        //then
        assertThat(solvedQuizInfo.score()).isEqualTo(Level.getPoint(IMAGE_QUIZ_ANIMAL1.getLevel()));
        assertThat(solvedQuizInfo.category()).isEqualTo(IMAGE_QUIZ_ANIMAL1.getQuizCategory());
    }

    @Test
    @DisplayName("풀지 않은 ImageQuiz 풀었을 때")
    public void solveImageQuiz3() {
        //given
        QuizSolvedRequest request =
                new QuizSolvedRequest(IMAGE_QUIZ_ANIMAL1.getQuizId(), IMAGE_QUIZ_ANIMAL1.getAnswer());

        given(imageQuizRepository.findById(IMAGE_QUIZ_ANIMAL1.getQuizId())).willReturn(Optional.of(IMAGE_QUIZ_ANIMAL1));
        given(quizSolvedRepository.existsByImageQuizAndMember(IMAGE_QUIZ_ANIMAL1, MEMBER1)).willReturn(false);

        //when
        SolvedQuizInfo solvedQuizInfo = imageQuizSolver.solveQuiz(request, MEMBER1);

        //then
        assertThat(solvedQuizInfo.score()).isEqualTo(Level.getPoint(IMAGE_QUIZ_ANIMAL1.getLevel()));
        assertThat(solvedQuizInfo.category()).isEqualTo(IMAGE_QUIZ_ANIMAL1.getQuizCategory());
    }
}