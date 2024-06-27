package soongsil.kidbean.server.quizsolve.application.morphemeChecker;

import java.util.Map;
import soongsil.kidbean.server.quizsolve.application.vo.type.MorphemeCheckEnum;

public interface MorphemeChecker {
    void analyzeMorpheme(Map<MorphemeCheckEnum, Boolean> checkList, String type,
                         String lemma);

    default void updateCheckListTrue(Map<MorphemeCheckEnum, Boolean> checkList, MorphemeCheckEnum checkEnum) {
        checkList.put(checkEnum, Boolean.TRUE);
    }
}
