package soongsil.kidbean.server.quizsolve.application.vo;

import lombok.Builder;

@Builder
public record UseWordVO(
        String word,
        Long count
) {
}
