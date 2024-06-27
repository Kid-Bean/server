package soongsil.kidbean.server.quizsolve.application;

import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soongsil.kidbean.server.quizsolve.application.morphemeChecker.MorphemeChecker;
import soongsil.kidbean.server.quizsolve.application.morphemeChecker.MorphemeCheckerFactory;
import soongsil.kidbean.server.quizsolve.application.vo.ApiResponseVO.ReturnObject.Sentence.MorphemeVO;
import soongsil.kidbean.server.quizsolve.application.vo.MorphemeCheckListResponse;
import soongsil.kidbean.server.quizsolve.application.vo.type.MorphemeCheckEnum;
import soongsil.kidbean.server.quizsolve.application.vo.type.MorphemeTypeEnum;

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
