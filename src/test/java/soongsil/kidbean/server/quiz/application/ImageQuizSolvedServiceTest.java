package soongsil.kidbean.server.quiz.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static soongsil.kidbean.server.member.fixture.MemberFixture.MEMBER;
import static soongsil.kidbean.server.quiz.fixture.ImageQuizFixture.IMAGE_QUIZ_ANIMAL;
import static soongsil.kidbean.server.quiz.fixture.ImageQuizSolvedFixture.IMAGE_QUIZ_SOLVED_ANIMAL_FALSE;
import static soongsil.kidbean.server.quiz.fixture.ImageQuizSolvedFixture.IMAGE_QUIZ_SOLVED_ANIMAL_TRUE;

import java.util.Collections;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import soongsil.kidbean.server.quiz.domain.type.Level;
import soongsil.kidbean.server.quiz.dto.request.ImageQuizSolvedRequest;
import soongsil.kidbean.server.quiz.repository.ImageQuizRepository;
import soongsil.kidbean.server.quiz.repository.ImageQuizSolvedRepository;

@ExtendWith(MockitoExtension.class)
class ImageQuizSolvedServiceTest {

    @Mock
    private ImageQuizSolvedRepository imageQuizSolvedRepository;

    @Mock
    private ImageQuizRepository imageQuizRepository;

    @InjectMocks
    private ImageQuizSolvedService imageQuizSolvedService;

    @Test
    public void 이미_푼_맞은_ImageQuizSolved를_풀었을_때() {
        //given
        ImageQuizSolvedRequest request =
                new ImageQuizSolvedRequest(IMAGE_QUIZ_ANIMAL.getQuizId(), IMAGE_QUIZ_ANIMAL.getAnswer());

        given(imageQuizRepository.findById(IMAGE_QUIZ_ANIMAL.getQuizId()))
                .willReturn(Optional.of(IMAGE_QUIZ_ANIMAL));
        //이전에 풀었던 문제
        given(imageQuizSolvedRepository.existsImageQuizSolvedByImageQuizAndMember(IMAGE_QUIZ_ANIMAL, MEMBER))
                .willReturn(true);
        given(imageQuizSolvedRepository.findByImageQuizAndMember(IMAGE_QUIZ_ANIMAL, MEMBER))
                .willReturn(Optional.of(IMAGE_QUIZ_SOLVED_ANIMAL_TRUE));

        //when
        Long totalScore = imageQuizSolvedService.solveImageQuizzes(
                Collections.singletonList(request), MEMBER);

        //then
        assertThat(totalScore).isEqualTo(0L);
    }

    @Test
    public void 이미_푼_틀린_ImageQuizSolved를_풀었을_때() {
        //given
        ImageQuizSolvedRequest request =
                new ImageQuizSolvedRequest(IMAGE_QUIZ_ANIMAL.getQuizId(), IMAGE_QUIZ_ANIMAL.getAnswer());

        given(imageQuizRepository.findById(IMAGE_QUIZ_ANIMAL.getQuizId()))
                .willReturn(Optional.of(IMAGE_QUIZ_ANIMAL));
        given(imageQuizSolvedRepository.existsImageQuizSolvedByImageQuizAndMember(IMAGE_QUIZ_ANIMAL, MEMBER))
                .willReturn(true);
        given(imageQuizSolvedRepository.findByImageQuizAndMember(IMAGE_QUIZ_ANIMAL, MEMBER))
                .willReturn(Optional.of(IMAGE_QUIZ_SOLVED_ANIMAL_FALSE));

        //when
        Long totalScore = imageQuizSolvedService.solveImageQuizzes(
                Collections.singletonList(request), MEMBER);

        //then
        assertThat(totalScore).isEqualTo(Level.getPoint(IMAGE_QUIZ_ANIMAL.getLevel()));
    }

    @Test
    public void 풀지_않은_ImageQuizSolved를_풀었을_때() {
        //given
        ImageQuizSolvedRequest request =
                new ImageQuizSolvedRequest(IMAGE_QUIZ_ANIMAL.getQuizId(), IMAGE_QUIZ_ANIMAL.getAnswer());

        given(imageQuizRepository.findById(IMAGE_QUIZ_ANIMAL.getQuizId()))
                .willReturn(Optional.of(IMAGE_QUIZ_ANIMAL));
        given(imageQuizSolvedRepository.existsImageQuizSolvedByImageQuizAndMember(IMAGE_QUIZ_ANIMAL, MEMBER))
                .willReturn(false);

        //when
        Long totalScore = imageQuizSolvedService.solveImageQuizzes(
                Collections.singletonList(request), MEMBER);

        //then
        assertThat(totalScore).isEqualTo(Level.getPoint(IMAGE_QUIZ_ANIMAL.getLevel()));
    }
}
