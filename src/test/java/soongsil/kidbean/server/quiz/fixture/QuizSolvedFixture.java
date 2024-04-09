package soongsil.kidbean.server.quiz.fixture;

import static soongsil.kidbean.server.member.fixture.MemberFixture.MEMBER;
import static soongsil.kidbean.server.quiz.fixture.ImageQuizFixture.IMAGE_QUIZ_ANIMAL;

import org.springframework.test.util.ReflectionTestUtils;
import soongsil.kidbean.server.quiz.domain.QuizSolved;

public class QuizSolvedFixture {

    public static final QuizSolved IMAGE_QUIZ_SOLVED_ANIMAL_FALSE = QuizSolved.builder()
            .imageQuiz(IMAGE_QUIZ_ANIMAL)
            .isCorrect(false)
            .member(MEMBER)
            .reply("asd")
            .build();

    public static final QuizSolved IMAGE_QUIZ_SOLVED_ANIMAL_TRUE = QuizSolved.builder()
            .imageQuiz(IMAGE_QUIZ_ANIMAL)
            .isCorrect(true)
            .member(MEMBER)
            .reply(IMAGE_QUIZ_ANIMAL.getAnswer())
            .build();

    static {
        ReflectionTestUtils.setField(IMAGE_QUIZ_SOLVED_ANIMAL_FALSE, "solvedId", 1L);
        ReflectionTestUtils.setField(IMAGE_QUIZ_SOLVED_ANIMAL_TRUE, "solvedId", 2L);
    }
}
