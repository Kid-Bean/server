package soongsil.kidbean.server.quiz.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static soongsil.kidbean.server.member.fixture.MemberFixture.MEMBER;
import static soongsil.kidbean.server.quiz.fixture.ImageQuizFixture.IMAGE_QUIZ_ANIMAL;
import static soongsil.kidbean.server.quiz.fixture.QuizSolvedFixture.IMAGE_QUIZ_SOLVED_ANIMAL_FALSE;
import static soongsil.kidbean.server.quiz.fixture.QuizSolvedFixture.IMAGE_QUIZ_SOLVED_ANIMAL_TRUE;

import java.util.Collections;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import soongsil.kidbean.server.quiz.domain.type.Level;
import soongsil.kidbean.server.quiz.dto.request.ImageQuizSolvedRequest;
import soongsil.kidbean.server.quiz.repository.ImageQuizRepository;
import soongsil.kidbean.server.quiz.repository.QuizSolvedRepository;

@ExtendWith(MockitoExtension.class)
class QuizSolvedServiceTest {

    @Mock
    private QuizSolvedRepository quizSolvedRepository;

    @Mock
    private ImageQuizRepository imageQuizRepository;

    @InjectMocks
    private QuizSolvedService quizSolvedService;

    @Test
    @DisplayName("이미 푼 맞은 ImageQuizSolved 풀었을 때")
    public void solveImageQuizzes1() {
        //given
        ImageQuizSolvedRequest request =
                new ImageQuizSolvedRequest(IMAGE_QUIZ_ANIMAL.getQuizId(), IMAGE_QUIZ_ANIMAL.getAnswer());

        given(imageQuizRepository.findById(IMAGE_QUIZ_ANIMAL.getQuizId()))
                .willReturn(Optional.of(IMAGE_QUIZ_ANIMAL));
        //이전에 풀었던 문제
        given(quizSolvedRepository.existsImageQuizSolvedByImageQuizAndMember(IMAGE_QUIZ_ANIMAL, MEMBER))
                .willReturn(true);
        given(quizSolvedRepository.findByImageQuizAndMember(IMAGE_QUIZ_ANIMAL, MEMBER))
                .willReturn(Optional.of(IMAGE_QUIZ_SOLVED_ANIMAL_TRUE));

        //when
        Long totalScore = quizSolvedService.solveImageQuizzes(
                Collections.singletonList(request), MEMBER);

        //then
        assertThat(totalScore).isEqualTo(0L);
    }

    @Test
    @DisplayName("이미 푼 틀린 ImageQuizSolved 풀었을 때")
    public void solveImageQuizzes2() {
        //given
        ImageQuizSolvedRequest request =
                new ImageQuizSolvedRequest(IMAGE_QUIZ_ANIMAL.getQuizId(), IMAGE_QUIZ_ANIMAL.getAnswer());

        given(imageQuizRepository.findById(IMAGE_QUIZ_ANIMAL.getQuizId()))
                .willReturn(Optional.of(IMAGE_QUIZ_ANIMAL));
        given(quizSolvedRepository.existsImageQuizSolvedByImageQuizAndMember(IMAGE_QUIZ_ANIMAL, MEMBER))
                .willReturn(true);
        given(quizSolvedRepository.findByImageQuizAndMember(IMAGE_QUIZ_ANIMAL, MEMBER))
                .willReturn(Optional.of(IMAGE_QUIZ_SOLVED_ANIMAL_FALSE));

        //when
        Long totalScore = quizSolvedService.solveImageQuizzes(
                Collections.singletonList(request), MEMBER);

        //then
        assertThat(totalScore).isEqualTo(Level.getPoint(IMAGE_QUIZ_ANIMAL.getLevel()));
    }

    @Test
    @DisplayName("풀지 않 ImageQuizSolved 풀었을 때")
    public void solveImageQuizzes3() {
        //given
        ImageQuizSolvedRequest request =
                new ImageQuizSolvedRequest(IMAGE_QUIZ_ANIMAL.getQuizId(), IMAGE_QUIZ_ANIMAL.getAnswer());

        given(imageQuizRepository.findById(IMAGE_QUIZ_ANIMAL.getQuizId()))
                .willReturn(Optional.of(IMAGE_QUIZ_ANIMAL));
        given(quizSolvedRepository.existsImageQuizSolvedByImageQuizAndMember(IMAGE_QUIZ_ANIMAL, MEMBER))
                .willReturn(false);

        //when
        Long totalScore = quizSolvedService.solveImageQuizzes(
                Collections.singletonList(request), MEMBER);

        //then
        assertThat(totalScore).isEqualTo(Level.getPoint(IMAGE_QUIZ_ANIMAL.getLevel()));
    }
}
