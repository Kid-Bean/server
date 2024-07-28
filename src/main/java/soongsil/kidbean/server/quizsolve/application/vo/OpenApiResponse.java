package soongsil.kidbean.server.quizsolve.application.vo;

import java.util.List;
import lombok.Builder;
import soongsil.kidbean.server.quizsolve.application.vo.ApiResponseVO.ReturnObject.Sentence.MorphemeVO;

@Builder
public record OpenApiResponse(
        List<MorphemeVO> morphemeVOList,
        List<UseWordVO> useWordVOList
) {
}
