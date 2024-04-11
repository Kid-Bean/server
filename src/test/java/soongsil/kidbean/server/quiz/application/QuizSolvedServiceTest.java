package soongsil.kidbean.server.quiz.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static soongsil.kidbean.server.member.fixture.MemberFixture.MEMBER;
import static soongsil.kidbean.server.quiz.fixture.ImageQuizFixture.IMAGE_QUIZ_ANIMAL;
import static soongsil.kidbean.server.quiz.fixture.WordQuizFixture.WORD_QUIZ;

import java.util.Collections;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import soongsil.kidbean.server.quiz.application.quizsolver.ImageQuizSolver;
import soongsil.kidbean.server.quiz.application.quizsolver.QuizSolverFactory;
import soongsil.kidbean.server.quiz.application.quizsolver.WordQuizSolver;
import soongsil.kidbean.server.quiz.application.vo.QuizType;
import soongsil.kidbean.server.quiz.domain.type.Level;
import soongsil.kidbean.server.quiz.dto.request.QuizSolvedRequest;
import soongsil.kidbean.server.quiz.repository.ImageQuizRepository;
import soongsil.kidbean.server.quiz.repository.QuizSolvedRepository;
import soongsil.kidbean.server.quiz.repository.WordQuizRepository;

@ExtendWith(MockitoExtension.class)
class QuizSolvedServiceTest {

    @Mock
    private QuizSolvedRepository quizSolvedRepository;

    @Mock
    private ImageQuizRepository imageQuizRepository;

    @Mock
    private WordQuizRepository wordQuizRepository;

    @Mock
    private QuizSolverFactory quizSolverFactory;

    @InjectMocks
    private QuizSolvedService quizSolvedService;

    @Test
    @DisplayName("이미 푼 맞은 ImageQuiz 풀었을 때")
    public void solveImageQuizzes1() {
        //given
        QuizSolvedRequest request =
                new QuizSolvedRequest(IMAGE_QUIZ_ANIMAL.getQuizId(), IMAGE_QUIZ_ANIMAL.getAnswer());
        ImageQuizSolver imageQuizSolver = new ImageQuizSolver(imageQuizRepository, quizSolvedRepository);

        given(quizSolverFactory.getSolver(QuizType.IMAGE_QUIZ)).willReturn(imageQuizSolver);
        given(imageQuizRepository.findById(IMAGE_QUIZ_ANIMAL.getQuizId()))
                .willReturn(Optional.of(IMAGE_QUIZ_ANIMAL));
        //이전에 풀었던 문제
        given(quizSolvedRepository.existsByImageQuizAndMember(IMAGE_QUIZ_ANIMAL, MEMBER)).willReturn(true);
        given(quizSolvedRepository.existsByImageQuizAndMemberAndIsCorrect(IMAGE_QUIZ_ANIMAL, MEMBER, true))
                .willReturn(true);

        //when
        Long totalScore = quizSolvedService.solveQuizzes(
                Collections.singletonList(request), MEMBER, QuizType.IMAGE_QUIZ);

        //then
        assertThat(totalScore).isEqualTo(0L);
    }

    @Test
    @DisplayName("이미 푼 틀린 ImageQuiz 풀었을 때")
    public void solveImageQuizzes2() {
        //given
        QuizSolvedRequest request =
                new QuizSolvedRequest(IMAGE_QUIZ_ANIMAL.getQuizId(), IMAGE_QUIZ_ANIMAL.getAnswer());
        ImageQuizSolver imageQuizSolver = new ImageQuizSolver(imageQuizRepository, quizSolvedRepository);

        given(quizSolverFactory.getSolver(QuizType.IMAGE_QUIZ)).willReturn(imageQuizSolver);
        given(imageQuizRepository.findById(IMAGE_QUIZ_ANIMAL.getQuizId())).willReturn(Optional.of(IMAGE_QUIZ_ANIMAL));
        given(quizSolvedRepository.existsByImageQuizAndMember(IMAGE_QUIZ_ANIMAL, MEMBER)).willReturn(true);
        given(quizSolvedRepository.existsByImageQuizAndMemberAndIsCorrect(IMAGE_QUIZ_ANIMAL, MEMBER, true))
                .willReturn(true);

        //when
        Long totalScore = quizSolvedService.solveQuizzes(
                Collections.singletonList(request), MEMBER, QuizType.IMAGE_QUIZ);

        //then
        assertThat(totalScore).isEqualTo(Level.getPoint(IMAGE_QUIZ_ANIMAL.getLevel()));
    }

    @Test
    @DisplayName("풀지 않은 ImageQuiz 풀었을 때")
    public void solveImageQuizzes3() {
        //given
        QuizSolvedRequest request =
                new QuizSolvedRequest(IMAGE_QUIZ_ANIMAL.getQuizId(), IMAGE_QUIZ_ANIMAL.getAnswer());
        ImageQuizSolver imageQuizSolver = new ImageQuizSolver(imageQuizRepository, quizSolvedRepository);

        given(quizSolverFactory.getSolver(QuizType.IMAGE_QUIZ)).willReturn(imageQuizSolver);
        given(imageQuizRepository.findById(IMAGE_QUIZ_ANIMAL.getQuizId())).willReturn(Optional.of(IMAGE_QUIZ_ANIMAL));
        given(quizSolvedRepository.existsByImageQuizAndMember(IMAGE_QUIZ_ANIMAL, MEMBER)).willReturn(false);

        //when
        Long totalScore = quizSolvedService.solveQuizzes(
                Collections.singletonList(request), MEMBER, QuizType.IMAGE_QUIZ);

        //then
        assertThat(totalScore).isEqualTo(Level.getPoint(IMAGE_QUIZ_ANIMAL.getLevel()));
    }

    @Test
    @DisplayName("이미 푼 맞은 WordQuiz 풀었을 때")
    public void solveWordQuizzes1() {
        //given
        QuizSolvedRequest request =
                new QuizSolvedRequest(WORD_QUIZ.getQuizId(), WORD_QUIZ.getAnswer());
        WordQuizSolver wordQuizSolver = new WordQuizSolver(wordQuizRepository, quizSolvedRepository);

        given(quizSolverFactory.getSolver(QuizType.WORD_QUIZ)).willReturn(wordQuizSolver);
        given(wordQuizRepository.findById(WORD_QUIZ.getQuizId())).willReturn(Optional.of(WORD_QUIZ));
        //이전에 풀었던 문제
        given(quizSolvedRepository.existsByWordQuizAndMember(WORD_QUIZ, MEMBER)).willReturn(true);
        given(quizSolvedRepository.existsByWordQuizAndMemberAndIsCorrect(WORD_QUIZ, MEMBER, true))
                .willReturn(true);

        //when
        Long totalScore = quizSolvedService.solveQuizzes(
                Collections.singletonList(request), MEMBER, QuizType.WORD_QUIZ);

        //then
        assertThat(totalScore).isEqualTo(0L);
    }

    @Test
    @DisplayName("이미 푼 틀린 WordQuiz 풀었을 때")
    public void solveWordQuizzes2() {
        //given
        QuizSolvedRequest request =
                new QuizSolvedRequest(WORD_QUIZ.getQuizId(), WORD_QUIZ.getAnswer());
        WordQuizSolver wordQuizSolver = new WordQuizSolver(wordQuizRepository, quizSolvedRepository);

        given(quizSolverFactory.getSolver(QuizType.WORD_QUIZ)).willReturn(wordQuizSolver);
        given(wordQuizRepository.findById(WORD_QUIZ.getQuizId())).willReturn(Optional.of(WORD_QUIZ));
        given(quizSolvedRepository.existsByWordQuizAndMember(WORD_QUIZ, MEMBER)).willReturn(true);
        given(quizSolvedRepository.existsByWordQuizAndMemberAndIsCorrect(WORD_QUIZ, MEMBER, true))
                .willReturn(true);

        //when
        Long totalScore = quizSolvedService.solveQuizzes(
                Collections.singletonList(request), MEMBER, QuizType.WORD_QUIZ);

        //then
        assertThat(totalScore).isEqualTo(Level.getPoint(WORD_QUIZ.getLevel()));
    }

    @Test
    @DisplayName("풀지 않은 WordQuiz 풀었을 때")
    public void solveWordQuizzes3() {
        //given
        QuizSolvedRequest request =
                new QuizSolvedRequest(WORD_QUIZ.getQuizId(), WORD_QUIZ.getAnswer());
        WordQuizSolver wordQuizSolver = new WordQuizSolver(wordQuizRepository, quizSolvedRepository);

        given(quizSolverFactory.getSolver(QuizType.WORD_QUIZ)).willReturn(wordQuizSolver);
        given(wordQuizRepository.findById(WORD_QUIZ.getQuizId())).willReturn(Optional.of(WORD_QUIZ));
        given(quizSolvedRepository.existsByWordQuizAndMember(WORD_QUIZ, MEMBER)).willReturn(false);

        //when
        Long totalScore = quizSolvedService.solveQuizzes(
                Collections.singletonList(request), MEMBER, QuizType.WORD_QUIZ);

        //then
        assertThat(totalScore).isEqualTo(Level.getPoint(WORD_QUIZ.getLevel()));
    }
}
