package soongsil.kidbean.server.quiz.fixture;

import static soongsil.kidbean.server.member.fixture.MemberFixture.MEMBER;
import static soongsil.kidbean.server.quiz.fixture.SentenceQuizWordFixture.SENTENCE_QUIZ_WORD1;
import static soongsil.kidbean.server.quiz.fixture.SentenceQuizWordFixture.SENTENCE_QUIZ_WORD2;
import static soongsil.kidbean.server.quiz.fixture.SentenceQuizWordFixture.SENTENCE_QUIZ_WORD3;

import java.util.List;
import org.springframework.test.util.ReflectionTestUtils;
import soongsil.kidbean.server.quiz.domain.SentenceQuiz;

public class SentenceQuizFixture {

    public static final SentenceQuiz SENTENCE_QUIZ = SentenceQuiz.builder()
            .title("sentenceQuiz")
            .member(MEMBER)
            .words(List.of(SENTENCE_QUIZ_WORD1, SENTENCE_QUIZ_WORD2, SENTENCE_QUIZ_WORD3))
            .build();

    static {
        ReflectionTestUtils.setField(SENTENCE_QUIZ, "quizId", 1L);
    }
}
