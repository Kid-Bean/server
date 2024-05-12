package soongsil.kidbean.server.quiz.application.morphemeChecker;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Service;
import soongsil.kidbean.server.quiz.application.quizscorer.ImageQuizScorer;
import soongsil.kidbean.server.quiz.application.quizscorer.QuizScorer;
import soongsil.kidbean.server.quiz.application.quizscorer.WordQuizScorer;
import soongsil.kidbean.server.quiz.application.vo.QuizType;
import soongsil.kidbean.server.quiz.application.vo.type.MorphemeTypeEnum;

@Service
public class MorphemeCheckerFactory {
    private final Map<MorphemeTypeEnum, MorphemeChecker> checkerMap = new ConcurrentHashMap<>();

    public MorphemeCheckerFactory() {
        checkerMap.put(MorphemeTypeEnum.NOUN, new NounChecker());
        checkerMap.put(MorphemeTypeEnum.NP, new NPChecker());
        checkerMap.put(MorphemeTypeEnum.JKS, new JKSChecker());
        checkerMap.put(MorphemeTypeEnum.VV, new VVChecker());
        checkerMap.put(MorphemeTypeEnum.VA, new VAChecker());
        checkerMap.put(MorphemeTypeEnum.EC, new ECChecker());
        checkerMap.put(MorphemeTypeEnum.JX_JC, new JXJCChecker());
        checkerMap.put(MorphemeTypeEnum.MA, new MAChecker());
        checkerMap.put(MorphemeTypeEnum.EF, new EndingChecker());
    }

    public MorphemeChecker getChecker(MorphemeTypeEnum type) {
        return checkerMap.get(type);
    }
}
