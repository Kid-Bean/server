package soongsil.kidbean.server.quiz.application.vo;

import lombok.Builder;

@Builder
public record UseWordVO(
        String word,
        Long count
) {
}
