package soongsil.kidbean.server.quizsolve.application;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import soongsil.kidbean.server.quizsolve.application.morphemeChecker.MorphemeCheckerFactory;
import soongsil.kidbean.server.quizsolve.application.vo.ApiResponseVO.ReturnObject.Sentence.MorphemeVO;
import soongsil.kidbean.server.quizsolve.application.vo.MorphemeCheckListResponse;
import soongsil.kidbean.server.quizsolve.application.vo.MorphemeCheckListResponse.MorphemeCheckListInfo;
import soongsil.kidbean.server.quizsolve.application.vo.type.MorphemeCheckEnum;

class AnalyzeMorphemeServiceTest {

    private AnalyzeMorphemeService analyzeMorphemeService;

    @BeforeEach
    void setUp() {
        analyzeMorphemeService = new AnalyzeMorphemeService(new MorphemeCheckerFactory());
    }

    private boolean isUsed(MorphemeCheckListResponse response, MorphemeCheckEnum checkEnum) {
        return response.checkList().stream()
                .filter(info -> info.checkInfoContent().equals(checkEnum.getContent()))
                .map(MorphemeCheckListInfo::isUsed)
                .findFirst()
                .orElseThrow();
    }

    @Test
    @DisplayName("모든 MorphemeCheckEnum 개수만큼 항목을 반환한다")
    void createMorphemeCheckList_returnsAllEntries() {
        MorphemeCheckListResponse response =
                analyzeMorphemeService.createMorphemeCheckList(List.of());

        assertThat(response.checkList()).hasSize(MorphemeCheckEnum.values().length);
    }

    @Test
    @DisplayName("명사와 동사를 분석해 해당 플래그를 true 로 설정한다")
    void createMorphemeCheckList_nounAndVerb() {
        List<MorphemeVO> morphemes = List.of(
                new MorphemeVO("사과", "NNG"),
                new MorphemeVO("달려가", "VV")
        );

        MorphemeCheckListResponse response =
                analyzeMorphemeService.createMorphemeCheckList(morphemes);

        assertThat(isUsed(response, MorphemeCheckEnum.USE_NOUN)).isTrue();
        assertThat(isUsed(response, MorphemeCheckEnum.USE_VV_VA_1)).isTrue();
        assertThat(isUsed(response, MorphemeCheckEnum.USE_VV_VA_2)).isTrue();
    }

    @Test
    @DisplayName("EMPTY 로 매핑되는 형태소는 건너뛴다")
    void createMorphemeCheckList_skipsEmptyType() {
        List<MorphemeVO> morphemes = List.of(
                new MorphemeVO("사과", "NNG"),
                new MorphemeVO("???", "UNKNOWN_TYPE")
        );

        MorphemeCheckListResponse response =
                analyzeMorphemeService.createMorphemeCheckList(morphemes);

        assertThat(isUsed(response, MorphemeCheckEnum.USE_NOUN)).isTrue();
        // 분석되지 않은 다른 플래그는 false 로 유지된다
        assertThat(isUsed(response, MorphemeCheckEnum.USE_VV_VA_1)).isFalse();
        assertThat(isUsed(response, MorphemeCheckEnum.USE_JKS_1)).isFalse();
    }
}
