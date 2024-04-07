package soongsil.kidbean.server.quiz.application.vo;

import lombok.Builder;

@Builder
public record WordCount(
        String word,
        Integer count
) {
}
