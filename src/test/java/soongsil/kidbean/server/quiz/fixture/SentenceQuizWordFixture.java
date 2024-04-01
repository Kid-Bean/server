package soongsil.kidbean.server.quiz.fixture;

import static soongsil.kidbean.server.quiz.fixture.SentenceQuizFixture.SENTENCE_QUIZ;

import soongsil.kidbean.server.quiz.domain.SentenceQuizWord;

public class SentenceQuizWordFixture {

    public static final SentenceQuizWord SENTENCE_QUIZ_WORD1 =
            SentenceQuizWord.builder()
                    .quiz(SENTENCE_QUIZ)
                    .content("content1")
                    .build();
    public static final SentenceQuizWord SENTENCE_QUIZ_WORD2 =
            SentenceQuizWord.builder()
                    .quiz(SENTENCE_QUIZ)
                    .content("content2")
                    .build();
    public static final SentenceQuizWord SENTENCE_QUIZ_WORD3 =
            SentenceQuizWord.builder()
                    .quiz(SENTENCE_QUIZ)
                    .content("content3")
                    .build();
}
