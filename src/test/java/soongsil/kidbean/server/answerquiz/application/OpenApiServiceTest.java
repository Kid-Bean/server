package soongsil.kidbean.server.answerquiz.application;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import soongsil.kidbean.server.quizsolve.application.vo.ApiResponseVO;
import soongsil.kidbean.server.quizsolve.application.vo.ApiResponseVO.ReturnObject.Sentence.MorphemeVO;
import soongsil.kidbean.server.quizsolve.application.vo.OpenApiResponse;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class OpenApiServiceTest {

    @Mock
    private OpenApiClient openApiClient;

    @InjectMocks
    private OpenApiService openApiService;

    @Test
    void analyzeAnswerTest() {
        //given
        String testAnswer = "테스트 문장입니다.";
        ApiResponseVO mockResponse = makeMockResponse();

        given(openApiClient.analyzeText(anyMap())).willReturn(mockResponse);

        //when
        OpenApiResponse response = openApiService.analyzeAnswer(testAnswer);

        //then
        assertThat(response.morphemeVOList()).isNotEmpty();
        assertThat(response.useWordVOList()).isNotEmpty();
        assertThat(response.morphemeVOList().size()).isEqualTo(2);
        assertThat(response.useWordVOList().size()).isEqualTo(2);
        assertThat(response.useWordVOList().get(0).word()).isEqualTo("test");
        assertThat(response.useWordVOList().get(0).count()).isEqualTo(1);
    }

    private ApiResponseVO makeMockResponse() {

        MorphemeVO morpheme1 = new MorphemeVO("test", "NNG");
        MorphemeVO morpheme2 = new MorphemeVO("text", "NNG");

        ApiResponseVO.ReturnObject.Sentence sentence1 = new ApiResponseVO.ReturnObject.Sentence(List.of(morpheme1));
        ApiResponseVO.ReturnObject.Sentence sentence2 = new ApiResponseVO.ReturnObject.Sentence(List.of(morpheme2));

        ApiResponseVO.ReturnObject returnObject = new ApiResponseVO.ReturnObject(List.of(sentence1, sentence2));

        return new ApiResponseVO(returnObject);
    }
}
