package soongsil.kidbean.server.quiz.dto.response;

import soongsil.kidbean.server.quiz.domain.Word;

public record WordQuizWordResponse(
        String content
) {
    public static WordQuizWordResponse from(Word word) {
        return new WordQuizWordResponse(word.getContent());
    }
}
