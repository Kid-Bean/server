package soongsil.kidbean.server.quiz.application;

import static soongsil.kidbean.server.quiz.application.vo.type.MorphemeCheckEnum.USE_EC_1;

import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soongsil.kidbean.server.quiz.application.morphemeChecker.MorphemeChecker;
import soongsil.kidbean.server.quiz.application.morphemeChecker.MorphemeCheckerFactory;
import soongsil.kidbean.server.quiz.application.vo.ApiResponseVO.ReturnObject.Sentence.MorphemeVO;
import soongsil.kidbean.server.quiz.application.vo.MorphemeCheckListResponse;
import soongsil.kidbean.server.quiz.application.vo.type.MorphemeCheckEnum;
import soongsil.kidbean.server.quiz.application.vo.type.MorphemeTypeEnum;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AnalyzeMorphemeService {

    private final MorphemeCheckerFactory morphemeCheckerFactory;

    public MorphemeCheckListResponse createMorphemeCheckList(List<MorphemeVO> morphemeVOS) {
        Map<MorphemeCheckEnum, Boolean> checkList = MorphemeCheckEnum.createDefaultCheckList();

        for (MorphemeVO morpheme : morphemeVOS) {
            String lemma = morpheme.lemma();
            String type = morpheme.type();

            MorphemeTypeEnum typeEnum = MorphemeTypeEnum.findByMorpheme(type);
            if (MorphemeTypeEnum.EMPTY == typeEnum) {
                continue;
            }

            MorphemeChecker morphemeChecker = morphemeCheckerFactory.getChecker(typeEnum);
            morphemeChecker.analyzeMorpheme(checkList, type, lemma);
        }

        return MorphemeCheckListResponse.from(checkList);
    }
}
