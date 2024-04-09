package soongsil.kidbean.server.quiz.application.vo;

import java.util.List;
import lombok.Builder;

@Builder
public record OpenApiResponse(
        List<MorphemeVO> morphemeVOList,
        List<UseWordVO> useWordVOList
) {
}
