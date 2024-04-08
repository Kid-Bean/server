package soongsil.kidbean.server.quiz.application.vo;

import lombok.Builder;

@Builder
public record UseWord(
        String word,
        Integer count
) {
}
