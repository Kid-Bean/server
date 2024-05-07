package soongsil.kidbean.server.quiz.application.vo.type;

import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import soongsil.kidbean.server.summary.domain.type.AgeGroup;
@Getter
public enum MorphemeCheckEnum {
    USE_ENDING_1("-야, -자 같은 종결어미를 사용한다", AgeGroup.TWO),
    USE_ENDING_2("복잡한 종결어미를 사용했다", AgeGroup.THREE),
    USE_ENDING_3("종결어미에 높임말을 사용한다", AgeGroup.AFTER_FIVE),

    USE_NOUN("명사를 사용한다", AgeGroup.TWO),
    USE_VV_VA_1("간단한 동사/형용사를 사용한다", AgeGroup.TWO),
    USE_VV_VA_2("복잡한 동사/형용사를 사용한다", AgeGroup.FOUR),
    USE_VA_1("‘이렇게’, ‘저렇게’ 등의 의문사를 사용한다", AgeGroup.FOUR),
    USE_JKS_1("주격조사 '-가'를 사용한다", AgeGroup.TWO),
    USE_JKS_2("복잡한 주격조사를 사용한다", AgeGroup.THREE),
    USE_NP_1("대명사로 자신을 지칭할 수 있다", AgeGroup.TWO),
    USE_NP_2("복잡한 대명사를 사용한다", AgeGroup.THREE),
    USE_NNB_1("의존명사 '-것'을 사용한다", AgeGroup.TWO),
    USE_NNB_2("복잡한 의존명사를 사용한다", AgeGroup.THREE),
    USE_EC_1("'-고' 등 간단한 연결어미를 사용한다", AgeGroup.THREE),
    USE_EC_2("복잡한 연결어미를 사용한다", AgeGroup.AFTER_FIVE),
    USE_MA_1("간단한 부사를 사용한다", AgeGroup.THREE),
    USE_MA_2("복잡한 부사를 사용한다", AgeGroup.AFTER_FIVE),
    USE_J_1("보조사를 사용할 수 있다", AgeGroup.THREE),
    USE_J_2("2가지 사물, 사람을 표현할 때 ‘-랑’, ‘-하고’, ‘-와’, ‘-고’를 사용한다", AgeGroup.AFTER_FIVE),

    ;
    private final String content;
    private final AgeGroup ageGroup;

    MorphemeCheckEnum(String content, AgeGroup ageGroup) {
        this.content = content;
        this.ageGroup = ageGroup;
    }

    public static Map<MorphemeCheckEnum, Boolean> createDefaultCheckList() {
        Map<MorphemeCheckEnum, Boolean> map = new HashMap<>();

        for (MorphemeCheckEnum checkEnum : MorphemeCheckEnum.values()) {
            map.put(checkEnum, false);
        }

        return map;
    }
}
