package soongsil.kidbean.server.quiz.application.vo;

import lombok.Builder;

@Builder
public record Morpheme(
        String morpheme,
        String type
) {
}