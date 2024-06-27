package soongsil.kidbean.server.wordquiz.dto.response;

import soongsil.kidbean.server.wordquiz.domain.Word;

public record WordResponse(
        String content
) {
    public static WordResponse from(Word word) {
        return new WordResponse(word.getContent());
    }
}
