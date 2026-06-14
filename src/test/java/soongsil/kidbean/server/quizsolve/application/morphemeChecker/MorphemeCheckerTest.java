package soongsil.kidbean.server.quizsolve.application.morphemeChecker;

import static org.assertj.core.api.Assertions.assertThat;
import static soongsil.kidbean.server.quizsolve.application.vo.type.MorphemeCheckEnum.USE_EC_1;
import static soongsil.kidbean.server.quizsolve.application.vo.type.MorphemeCheckEnum.USE_EC_2;
import static soongsil.kidbean.server.quizsolve.application.vo.type.MorphemeCheckEnum.USE_ENDING_1;
import static soongsil.kidbean.server.quizsolve.application.vo.type.MorphemeCheckEnum.USE_ENDING_2;
import static soongsil.kidbean.server.quizsolve.application.vo.type.MorphemeCheckEnum.USE_ENDING_3;
import static soongsil.kidbean.server.quizsolve.application.vo.type.MorphemeCheckEnum.USE_JKS_1;
import static soongsil.kidbean.server.quizsolve.application.vo.type.MorphemeCheckEnum.USE_JKS_2;
import static soongsil.kidbean.server.quizsolve.application.vo.type.MorphemeCheckEnum.USE_J_1;
import static soongsil.kidbean.server.quizsolve.application.vo.type.MorphemeCheckEnum.USE_J_2;
import static soongsil.kidbean.server.quizsolve.application.vo.type.MorphemeCheckEnum.USE_MA_1;
import static soongsil.kidbean.server.quizsolve.application.vo.type.MorphemeCheckEnum.USE_MA_2;
import static soongsil.kidbean.server.quizsolve.application.vo.type.MorphemeCheckEnum.USE_NNB_1;
import static soongsil.kidbean.server.quizsolve.application.vo.type.MorphemeCheckEnum.USE_NNB_2;
import static soongsil.kidbean.server.quizsolve.application.vo.type.MorphemeCheckEnum.USE_NOUN;
import static soongsil.kidbean.server.quizsolve.application.vo.type.MorphemeCheckEnum.USE_NP_1;
import static soongsil.kidbean.server.quizsolve.application.vo.type.MorphemeCheckEnum.USE_NP_2;
import static soongsil.kidbean.server.quizsolve.application.vo.type.MorphemeCheckEnum.USE_VA_1;
import static soongsil.kidbean.server.quizsolve.application.vo.type.MorphemeCheckEnum.USE_VV_VA_1;
import static soongsil.kidbean.server.quizsolve.application.vo.type.MorphemeCheckEnum.USE_VV_VA_2;

import java.util.EnumMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import soongsil.kidbean.server.quizsolve.application.vo.type.MorphemeCheckEnum;
import soongsil.kidbean.server.quizsolve.application.vo.type.MorphemeTypeEnum;

/**
 * Checker.java 안에 package-private 으로 정의된 각 MorphemeChecker 구현체를
 * MorphemeCheckerFactory 를 통해 가져와 분기 로직을 검증한다.
 */
class MorphemeCheckerTest {

    private final MorphemeCheckerFactory factory = new MorphemeCheckerFactory();

    private Map<MorphemeCheckEnum, Boolean> newCheckList() {
        return new EnumMap<>(MorphemeCheckEnum.class);
    }

    @Nested
    @DisplayName("NounChecker")
    class NounCheckerTest {

        private final MorphemeChecker checker = factory.getChecker(MorphemeTypeEnum.NOUN);

        @Test
        @DisplayName("일반 명사는 USE_NOUN 만 true 로 설정한다")
        void noun_general() {
            Map<MorphemeCheckEnum, Boolean> checkList = newCheckList();

            checker.analyzeMorpheme(checkList, "NNG", "사과");

            assertThat(checkList).containsEntry(USE_NOUN, Boolean.TRUE);
            assertThat(checkList).doesNotContainKeys(USE_NNB_1, USE_NNB_2);
        }

        @Test
        @DisplayName("의존명사 '것'은 USE_NNB_1 을 true 로 설정한다")
        void noun_nnb_geot() {
            Map<MorphemeCheckEnum, Boolean> checkList = newCheckList();

            checker.analyzeMorpheme(checkList, "NNB", "것");

            assertThat(checkList).containsEntry(USE_NOUN, Boolean.TRUE);
            assertThat(checkList).containsEntry(USE_NNB_1, Boolean.TRUE);
            assertThat(checkList).doesNotContainKey(USE_NNB_2);
        }

        @Test
        @DisplayName("'것'이 아닌 의존명사는 USE_NNB_2 를 true 로 설정한다")
        void noun_nnb_other() {
            Map<MorphemeCheckEnum, Boolean> checkList = newCheckList();

            checker.analyzeMorpheme(checkList, "NNB", "수");

            assertThat(checkList).containsEntry(USE_NOUN, Boolean.TRUE);
            assertThat(checkList).containsEntry(USE_NNB_2, Boolean.TRUE);
            assertThat(checkList).doesNotContainKey(USE_NNB_1);
        }
    }

    @Nested
    @DisplayName("NPChecker")
    class NPCheckerTest {

        private final MorphemeChecker checker = factory.getChecker(MorphemeTypeEnum.NP);

        @Test
        @DisplayName("'나' 또는 '저'는 USE_NP_1 을 true 로 설정한다")
        void np_self() {
            Map<MorphemeCheckEnum, Boolean> checkList = newCheckList();

            checker.analyzeMorpheme(checkList, "NP", "나");

            assertThat(checkList).containsEntry(USE_NP_1, Boolean.TRUE);
            assertThat(checkList).doesNotContainKey(USE_NP_2);
        }

        @Test
        @DisplayName("그 외 대명사는 USE_NP_2 를 true 로 설정한다")
        void np_other() {
            Map<MorphemeCheckEnum, Boolean> checkList = newCheckList();

            checker.analyzeMorpheme(checkList, "NP", "그것");

            assertThat(checkList).containsEntry(USE_NP_2, Boolean.TRUE);
            assertThat(checkList).doesNotContainKey(USE_NP_1);
        }
    }

    @Nested
    @DisplayName("JKSChecker")
    class JKSCheckerTest {

        private final MorphemeChecker checker = factory.getChecker(MorphemeTypeEnum.JKS);

        @Test
        @DisplayName("주격조사 '가'는 USE_JKS_1 을 true 로 설정한다")
        void jks_ga() {
            Map<MorphemeCheckEnum, Boolean> checkList = newCheckList();

            checker.analyzeMorpheme(checkList, "JKS", "가");

            assertThat(checkList).containsEntry(USE_JKS_1, Boolean.TRUE);
            assertThat(checkList).doesNotContainKey(USE_JKS_2);
        }

        @Test
        @DisplayName("그 외 주격조사는 USE_JKS_2 를 true 로 설정한다")
        void jks_other() {
            Map<MorphemeCheckEnum, Boolean> checkList = newCheckList();

            checker.analyzeMorpheme(checkList, "JKS", "이");

            assertThat(checkList).containsEntry(USE_JKS_2, Boolean.TRUE);
            assertThat(checkList).doesNotContainKey(USE_JKS_1);
        }
    }

    @Nested
    @DisplayName("VVChecker")
    class VVCheckerTest {

        private final MorphemeChecker checker = factory.getChecker(MorphemeTypeEnum.VV);

        @Test
        @DisplayName("짧은 동사는 USE_VV_VA_1 만 true 로 설정한다")
        void vv_short() {
            Map<MorphemeCheckEnum, Boolean> checkList = newCheckList();

            checker.analyzeMorpheme(checkList, "VV", "가");

            assertThat(checkList).containsEntry(USE_VV_VA_1, Boolean.TRUE);
            assertThat(checkList).doesNotContainKey(USE_VV_VA_2);
        }

        @Test
        @DisplayName("길이 2 초과 동사는 USE_VV_VA_2 도 true 로 설정한다")
        void vv_long() {
            Map<MorphemeCheckEnum, Boolean> checkList = newCheckList();

            checker.analyzeMorpheme(checkList, "VV", "달려가");

            assertThat(checkList).containsEntry(USE_VV_VA_1, Boolean.TRUE);
            assertThat(checkList).containsEntry(USE_VV_VA_2, Boolean.TRUE);
        }
    }

    @Nested
    @DisplayName("VAChecker")
    class VACheckerTest {

        private final MorphemeChecker checker = factory.getChecker(MorphemeTypeEnum.VA);

        @Test
        @DisplayName("짧은 형용사는 USE_VV_VA_1 만 true 로 설정한다")
        void va_short() {
            Map<MorphemeCheckEnum, Boolean> checkList = newCheckList();

            checker.analyzeMorpheme(checkList, "VA", "크");

            assertThat(checkList).containsEntry(USE_VV_VA_1, Boolean.TRUE);
            assertThat(checkList).doesNotContainKeys(USE_VV_VA_2, USE_VA_1);
        }

        @Test
        @DisplayName("길이 2 초과 형용사는 USE_VV_VA_2 도 true 로 설정한다")
        void va_long() {
            Map<MorphemeCheckEnum, Boolean> checkList = newCheckList();

            checker.analyzeMorpheme(checkList, "VA", "아름답");

            assertThat(checkList).containsEntry(USE_VV_VA_1, Boolean.TRUE);
            assertThat(checkList).containsEntry(USE_VV_VA_2, Boolean.TRUE);
        }

        @Test
        @DisplayName("VA_1_LIST('이렇')는 USE_VA_1 을 true 로 설정한다")
        void va_va1_list() {
            Map<MorphemeCheckEnum, Boolean> checkList = newCheckList();

            checker.analyzeMorpheme(checkList, "VA", "이렇");

            assertThat(checkList).containsEntry(USE_VV_VA_1, Boolean.TRUE);
            assertThat(checkList).containsEntry(USE_VA_1, Boolean.TRUE);
        }
    }

    @Nested
    @DisplayName("ECChecker")
    class ECCheckerTest {

        private final MorphemeChecker checker = factory.getChecker(MorphemeTypeEnum.EC);

        @Test
        @DisplayName("연결어미 '고'는 USE_EC_1 을 true 로 설정한다")
        void ec_go() {
            Map<MorphemeCheckEnum, Boolean> checkList = newCheckList();

            checker.analyzeMorpheme(checkList, "EC", "고");

            assertThat(checkList).containsEntry(USE_EC_1, Boolean.TRUE);
            assertThat(checkList).doesNotContainKey(USE_EC_2);
        }

        @Test
        @DisplayName("그 외 연결어미는 USE_EC_2 를 true 로 설정한다")
        void ec_other() {
            Map<MorphemeCheckEnum, Boolean> checkList = newCheckList();

            checker.analyzeMorpheme(checkList, "EC", "면서");

            assertThat(checkList).containsEntry(USE_EC_2, Boolean.TRUE);
            assertThat(checkList).doesNotContainKey(USE_EC_1);
        }
    }

    @Nested
    @DisplayName("JXJCChecker")
    class JXJCCheckerTest {

        private final MorphemeChecker checker = factory.getChecker(MorphemeTypeEnum.JX_JC);

        @Test
        @DisplayName("JC 타입이며 목록에 포함된 보조사는 USE_J_2 도 true 로 설정한다")
        void jc_in_list() {
            Map<MorphemeCheckEnum, Boolean> checkList = newCheckList();

            checker.analyzeMorpheme(checkList, "JC", "랑");

            assertThat(checkList).containsEntry(USE_J_1, Boolean.TRUE);
            assertThat(checkList).containsEntry(USE_J_2, Boolean.TRUE);
        }

        @Test
        @DisplayName("JC 타입이 아니면 USE_J_1 만 true 로 설정한다")
        void jx_only() {
            Map<MorphemeCheckEnum, Boolean> checkList = newCheckList();

            checker.analyzeMorpheme(checkList, "JX", "랑");

            assertThat(checkList).containsEntry(USE_J_1, Boolean.TRUE);
            assertThat(checkList).doesNotContainKey(USE_J_2);
        }

        @Test
        @DisplayName("JC 타입이지만 목록에 없으면 USE_J_1 만 true 로 설정한다")
        void jc_not_in_list() {
            Map<MorphemeCheckEnum, Boolean> checkList = newCheckList();

            checker.analyzeMorpheme(checkList, "JC", "이며");

            assertThat(checkList).containsEntry(USE_J_1, Boolean.TRUE);
            assertThat(checkList).doesNotContainKey(USE_J_2);
        }
    }

    @Nested
    @DisplayName("MAChecker")
    class MACheckerTest {

        private final MorphemeChecker checker = factory.getChecker(MorphemeTypeEnum.MA);

        @Test
        @DisplayName("짧은 부사는 USE_MA_1 만 true 로 설정한다")
        void ma_short() {
            Map<MorphemeCheckEnum, Boolean> checkList = newCheckList();

            checker.analyzeMorpheme(checkList, "MAG", "잘");

            assertThat(checkList).containsEntry(USE_MA_1, Boolean.TRUE);
            assertThat(checkList).doesNotContainKey(USE_MA_2);
        }

        @Test
        @DisplayName("길이 2 초과 부사는 USE_MA_2 도 true 로 설정한다")
        void ma_long() {
            Map<MorphemeCheckEnum, Boolean> checkList = newCheckList();

            checker.analyzeMorpheme(checkList, "MAG", "천천히");

            assertThat(checkList).containsEntry(USE_MA_1, Boolean.TRUE);
            assertThat(checkList).containsEntry(USE_MA_2, Boolean.TRUE);
        }
    }

    @Nested
    @DisplayName("EndingChecker")
    class EndingCheckerTest {

        private final MorphemeChecker checker = factory.getChecker(MorphemeTypeEnum.EF);

        @Test
        @DisplayName("'야','자' 종결어미는 USE_ENDING_1 을 true 로 설정한다")
        void ending_simple() {
            Map<MorphemeCheckEnum, Boolean> checkList = newCheckList();

            checker.analyzeMorpheme(checkList, "EF", "야");

            assertThat(checkList).containsEntry(USE_ENDING_1, Boolean.TRUE);
            assertThat(checkList).doesNotContainKeys(USE_ENDING_2, USE_ENDING_3);
        }

        @Test
        @DisplayName("'어요','요' 종결어미는 USE_ENDING_2 와 USE_ENDING_3 를 true 로 설정한다")
        void ending_honorific() {
            Map<MorphemeCheckEnum, Boolean> checkList = newCheckList();

            checker.analyzeMorpheme(checkList, "EF", "요");

            assertThat(checkList).containsEntry(USE_ENDING_2, Boolean.TRUE);
            assertThat(checkList).containsEntry(USE_ENDING_3, Boolean.TRUE);
            assertThat(checkList).doesNotContainKey(USE_ENDING_1);
        }

        @Test
        @DisplayName("목록에 없는 종결어미는 USE_ENDING_2 만 true 로 설정한다")
        void ending_other() {
            Map<MorphemeCheckEnum, Boolean> checkList = newCheckList();

            checker.analyzeMorpheme(checkList, "EF", "다");

            assertThat(checkList).containsEntry(USE_ENDING_2, Boolean.TRUE);
            assertThat(checkList).doesNotContainKeys(USE_ENDING_1, USE_ENDING_3);
        }
    }
}
