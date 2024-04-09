package soongsil.kidbean.server.quiz.fixture;

import static soongsil.kidbean.server.quiz.fixture.WordQuizFixture.WORD_QUIZ;

import org.springframework.test.util.ReflectionTestUtils;
import soongsil.kidbean.server.quiz.domain.Word;

public class WordFixture {

    public static final Word WORD1 = Word.builder()
            .wordQuiz(WORD_QUIZ)
            .content("content1")
            .build();
    public static final Word WORD2 = Word.builder()
            .wordQuiz(WORD_QUIZ)
            .content("content2")
            .build();
    public static final Word WORD3 = Word.builder()
            .wordQuiz(WORD_QUIZ)
            .content("content3")
            .build();

    static {
        ReflectionTestUtils.setField(WORD1, "wordId", 1L);
        ReflectionTestUtils.setField(WORD2, "wordId", 2L);
        ReflectionTestUtils.setField(WORD3, "wordId", 3L);
    }
}