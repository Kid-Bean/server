package soongsil.kidbean.server.quiz.dto.response;

import soongsil.kidbean.server.quiz.domain.Word;

public record WordResponse(
        String content
) {
    public static WordResponse from(Word word) {
        return new WordResponse(word.getContent());
    }
}
