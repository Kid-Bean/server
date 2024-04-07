package soongsil.kidbean.server.quiz.fixture;

import static soongsil.kidbean.server.quiz.fixture.WordQuizFixture.WORD_QUIZ;

import org.springframework.test.util.ReflectionTestUtils;
import soongsil.kidbean.server.quiz.domain.WordQuizWord;

public class WordQuizWordFixture {

    public static final WordQuizWord WORD_QUIZ_WORD1 = WordQuizWord.builder()
            .quiz(WORD_QUIZ)
            .content("content1")
            .build();
    public static final WordQuizWord WORD_QUIZ_WORD2 = WordQuizWord.builder()
            .quiz(WORD_QUIZ)
            .content("content2")
            .build();
    public static final WordQuizWord WORD_QUIZ_WORD3 = WordQuizWord.builder()
            .quiz(WORD_QUIZ)
            .content("content3")
            .build();

    static {
        ReflectionTestUtils.setField(WORD_QUIZ_WORD1, "wordId", 1L);
        ReflectionTestUtils.setField(WORD_QUIZ_WORD2, "wordId", 2L);
        ReflectionTestUtils.setField(WORD_QUIZ_WORD3, "wordId", 3L);
    }
}
