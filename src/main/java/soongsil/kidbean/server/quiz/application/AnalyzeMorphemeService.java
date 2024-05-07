package soongsil.kidbean.server.quiz.application;

import static soongsil.kidbean.server.quiz.application.vo.type.MorphemeCheckEnum.USE_EC_1;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import soongsil.kidbean.server.quiz.application.vo.ApiResponseVO.ReturnObject.Sentence.MorphemeVO;
import soongsil.kidbean.server.quiz.application.vo.MorphemeCheckListResponse;
import soongsil.kidbean.server.quiz.application.vo.type.MorphemeCheckEnum;

@Service
public class AnalyzeMorphemeService {
    public MorphemeCheckListResponse createMorphemeCheckList(List<MorphemeVO> morphemeVOS) {
        Map<MorphemeCheckEnum, Boolean> checkList = MorphemeCheckEnum.createDefaultCheckList();

        //checkList 바꾸기
        checkList.put(MorphemeCheckEnum.USE_EC_1, true);

        return MorphemeCheckListResponse.from(checkList);
    }
}
