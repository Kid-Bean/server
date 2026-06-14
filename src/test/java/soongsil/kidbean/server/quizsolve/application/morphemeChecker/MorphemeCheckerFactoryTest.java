package soongsil.kidbean.server.quizsolve.application.morphemeChecker;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import soongsil.kidbean.server.quizsolve.application.vo.type.MorphemeTypeEnum;

class MorphemeCheckerFactoryTest {

    private final MorphemeCheckerFactory factory = new MorphemeCheckerFactory();

    @Test
    @DisplayName("매핑된 타입은 알맞은 checker 를 반환한다")
    void getChecker_mappedTypes() {
        assertThat(factory.getChecker(MorphemeTypeEnum.NOUN)).isNotNull();
        assertThat(factory.getChecker(MorphemeTypeEnum.NP)).isNotNull();
        assertThat(factory.getChecker(MorphemeTypeEnum.JKS)).isNotNull();
        assertThat(factory.getChecker(MorphemeTypeEnum.VV)).isNotNull();
        assertThat(factory.getChecker(MorphemeTypeEnum.VA)).isNotNull();
        assertThat(factory.getChecker(MorphemeTypeEnum.EC)).isNotNull();
        assertThat(factory.getChecker(MorphemeTypeEnum.JX_JC)).isNotNull();
        assertThat(factory.getChecker(MorphemeTypeEnum.MA)).isNotNull();
        assertThat(factory.getChecker(MorphemeTypeEnum.EF)).isNotNull();
    }

    @Test
    @DisplayName("매핑되지 않은 타입(EMPTY)은 null 을 반환한다")
    void getChecker_unmappedReturnsNull() {
        assertThat(factory.getChecker(MorphemeTypeEnum.EMPTY)).isNull();
    }

    @Test
    @DisplayName("의존 명사(NNB) 타입은 매핑되어 있지 않아 null 을 반환한다")
    void getChecker_nnbReturnsNull() {
        assertThat(factory.getChecker(MorphemeTypeEnum.NNB)).isNull();
    }
}
