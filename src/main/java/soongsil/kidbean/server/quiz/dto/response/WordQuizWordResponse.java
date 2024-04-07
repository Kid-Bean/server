package soongsil.kidbean.server.quiz.dto.response;

import soongsil.kidbean.server.quiz.domain.WordQuizWord;

public record WordQuizWordResponse(
        String content
) {
    public static WordQuizWordResponse from(WordQuizWord word) {
        return new WordQuizWordResponse(word.getContent());
    }
}
