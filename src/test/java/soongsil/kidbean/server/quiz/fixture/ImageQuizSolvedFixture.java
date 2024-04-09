package soongsil.kidbean.server.quiz.fixture;

import static soongsil.kidbean.server.member.fixture.MemberFixture.MEMBER;
import static soongsil.kidbean.server.quiz.fixture.ImageQuizFixture.IMAGE_QUIZ_ANIMAL;

import org.springframework.test.util.ReflectionTestUtils;
import soongsil.kidbean.server.quiz.domain.ImageQuizSolved;

public class ImageQuizSolvedFixture {

    public static final ImageQuizSolved IMAGE_QUIZ_SOLVED_ANIMAL_FALSE = ImageQuizSolved.builder()
            .imageQuiz(IMAGE_QUIZ_ANIMAL)
            .isCorrect(false)
            .member(MEMBER)
            .answer("asd")
            .build();

    public static final ImageQuizSolved IMAGE_QUIZ_SOLVED_ANIMAL_TRUE = ImageQuizSolved.builder()
            .imageQuiz(IMAGE_QUIZ_ANIMAL)
            .isCorrect(true)
            .member(MEMBER)
            .answer(IMAGE_QUIZ_ANIMAL.getAnswer())
            .build();

    static {
        ReflectionTestUtils.setField(IMAGE_QUIZ_SOLVED_ANIMAL_FALSE, "solvedId", 1L);
        ReflectionTestUtils.setField(IMAGE_QUIZ_SOLVED_ANIMAL_TRUE, "solvedId", 2L);
    }
}
