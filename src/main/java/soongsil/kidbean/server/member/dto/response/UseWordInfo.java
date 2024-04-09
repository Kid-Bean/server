package soongsil.kidbean.server.member.dto.response;

import soongsil.kidbean.server.quiz.domain.UseWord;

public record UseWordInfo(
        String word,
        Long count
) {
    public static UseWordInfo from(UseWord useWord) {
        return new UseWordInfo(
                useWord.getWordName(),
                useWord.getCount()
        );
    }
}
