package soongsil.kidbean.server.quiz.fixture;

import static soongsil.kidbean.server.member.fixture.MemberFixture.MEMBER1;
import static soongsil.kidbean.server.quiz.fixture.ImageQuizFixture.IMAGE_QUIZ_ANIMAL1;
import static soongsil.kidbean.server.quiz.fixture.WordQuizFixture.WORD_QUIZ;

import org.springframework.test.util.ReflectionTestUtils;
import soongsil.kidbean.server.quiz.domain.QuizSolved;

public class QuizSolvedFixture {

    public static final QuizSolved IMAGE_QUIZ_SOLVED_ANIMAL_TRUE = QuizSolved.builder()
            .imageQuiz(IMAGE_QUIZ_ANIMAL1)
            .isCorrect(true)
            .member(MEMBER1)
            .reply(IMAGE_QUIZ_ANIMAL1.getAnswer())
            .build();

    public static final QuizSolved IMAGE_QUIZ_SOLVED_ANIMAL_FALSE = QuizSolved.builder()
            .imageQuiz(IMAGE_QUIZ_ANIMAL1)
            .isCorrect(false)
            .member(MEMBER1)
            .reply("false")
            .build();

    public static final QuizSolved WORD_QUIZ_SOLVED_TRUE = QuizSolved.builder()
            .wordQuiz(WORD_QUIZ)
            .isCorrect(true)
            .member(MEMBER1)
            .reply(WORD_QUIZ.getAnswer())
            .build();

    public static final QuizSolved WORD_QUIZ_SOLVED_FALSE = QuizSolved.builder()
            .wordQuiz(WORD_QUIZ)
            .isCorrect(false)
            .member(MEMBER1)
            .reply("false")
            .build();

    static {
        ReflectionTestUtils.setField(IMAGE_QUIZ_SOLVED_ANIMAL_TRUE, "solvedId", 1L);
        ReflectionTestUtils.setField(IMAGE_QUIZ_SOLVED_ANIMAL_FALSE, "solvedId", 2L);
        ReflectionTestUtils.setField(WORD_QUIZ_SOLVED_TRUE, "solvedId", 3L);
        ReflectionTestUtils.setField(WORD_QUIZ_SOLVED_FALSE, "solvedId", 4L);
    }
}
