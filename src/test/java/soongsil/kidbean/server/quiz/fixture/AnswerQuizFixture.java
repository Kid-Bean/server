package soongsil.kidbean.server.quiz.fixture;

import static soongsil.kidbean.server.member.fixture.MemberFixture.MEMBER1;

import org.springframework.test.util.ReflectionTestUtils;
import soongsil.kidbean.server.quiz.domain.AnswerQuiz;

public class AnswerQuizFixture {

    public static final AnswerQuiz ANSWER_QUIZ = AnswerQuiz.builder()
            .question("오늘 점심 뭐 먹었어?")
            .member(MEMBER1)
            .build();

    static {
        ReflectionTestUtils.setField(ANSWER_QUIZ, "quizId", 1L);
    }
}
