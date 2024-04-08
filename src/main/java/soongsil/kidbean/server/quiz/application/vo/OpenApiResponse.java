package soongsil.kidbean.server.quiz.application.vo;

import java.util.List;
import lombok.Builder;

@Builder
public record OpenApiResponse(
        List<Morpheme> morphemeList,
        List<UseWord> useWordList
) {
}
