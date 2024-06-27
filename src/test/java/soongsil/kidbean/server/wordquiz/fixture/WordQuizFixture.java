package soongsil.kidbean.server.wordquiz.fixture;

import static soongsil.kidbean.server.member.fixture.MemberFixture.MEMBER1;
import static soongsil.kidbean.server.wordquiz.fixture.WordFixture.WORD1;
import static soongsil.kidbean.server.wordquiz.fixture.WordFixture.WORD2;
import static soongsil.kidbean.server.wordquiz.fixture.WordFixture.WORD3;
import static soongsil.kidbean.server.wordquiz.fixture.WordFixture.WORD4;

import java.util.List;
import org.springframework.test.util.ReflectionTestUtils;
import soongsil.kidbean.server.wordquiz.domain.WordQuiz;
import soongsil.kidbean.server.quizsolve.domain.type.Level;

public class WordQuizFixture {

    public static final WordQuiz WORD_QUIZ = WordQuiz.builder()
            .title("WordQuiz")
            .level(Level.BRONZE)
            .member(MEMBER1)
            .answer("answer")
            .words(List.of(WORD1, WORD2, WORD3, WORD4))
            .build();

    static {
        ReflectionTestUtils.setField(WORD_QUIZ, "quizId", 1L);
    }
}
