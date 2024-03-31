package soongsil.kidbean.server.quiz.dto.response;

import soongsil.kidbean.server.quiz.domain.SentenceQuizWord;

public record SentenceQuizWordResponse(
        String content
) {
    public static SentenceQuizWordResponse from(SentenceQuizWord word) {
        return new SentenceQuizWordResponse(word.getContent());
    }
}
