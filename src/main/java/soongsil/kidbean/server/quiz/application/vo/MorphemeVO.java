package soongsil.kidbean.server.quiz.application.vo;

import lombok.Builder;

@Builder
public record MorphemeVO(
        String morpheme,
        String type
) {
}