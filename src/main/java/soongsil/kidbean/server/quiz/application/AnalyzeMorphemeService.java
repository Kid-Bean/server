package soongsil.kidbean.server.quiz.application;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import soongsil.kidbean.server.quiz.application.vo.ApiResponseVO.ReturnObject.Sentence.MorphemeVO;
import soongsil.kidbean.server.quiz.application.vo.type.MorphemeCheckEnum;

@Service
public class AnalyzeMorphemeService {
    public void createMorphemeCheckList(List<MorphemeVO> morphemeVOS) {
        Map<MorphemeCheckEnum, Boolean> checkList = MorphemeCheckEnum.createDefaultCheckList();


    }
}
