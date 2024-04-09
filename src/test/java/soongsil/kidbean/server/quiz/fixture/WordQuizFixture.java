package soongsil.kidbean.server.quiz.fixture;

import static soongsil.kidbean.server.member.fixture.MemberFixture.MEMBER;
import static soongsil.kidbean.server.quiz.fixture.WordQuizWordFixture.WORD1;
import static soongsil.kidbean.server.quiz.fixture.WordQuizWordFixture.WORD2;
import static soongsil.kidbean.server.quiz.fixture.WordQuizWordFixture.WORD3;

import java.util.List;
import org.springframework.test.util.ReflectionTestUtils;
import soongsil.kidbean.server.quiz.domain.WordQuiz;

public class WordQuizFixture {

    public static final WordQuiz WORD_QUIZ = WordQuiz.builder()
            .title("WordQuiz")
            .member(MEMBER)
            .words(List.of(WORD1, WORD2, WORD3))
            .build();

    static {
        ReflectionTestUtils.setField(WORD_QUIZ, "quizId", 1L);
    }
}
