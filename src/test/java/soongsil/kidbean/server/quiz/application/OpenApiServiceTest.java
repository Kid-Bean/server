package soongsil.kidbean.server.quiz.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.List;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.apache.http.HttpHeaders;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import soongsil.kidbean.server.quiz.application.config.MockWebClientConfig;
import soongsil.kidbean.server.quiz.application.vo.ApiResponseVO;
import soongsil.kidbean.server.quiz.application.vo.ApiResponseVO.ReturnObject.Sentence.MorphemeVO;
import soongsil.kidbean.server.quiz.application.vo.OpenApiResponse;

import static org.assertj.core.api.Assertions.assertThat;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Import(MockWebClientConfig.class)
@ActiveProfiles("test")
@SpringBootTest(classes = {OpenApiService.class})
public class OpenApiServiceTest {

    @Autowired
    private OpenApiService openApiService;

    ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockWebServer mockWebServer;

    @AfterAll
    void tearDown() throws IOException {
        mockWebServer.shutdown();
    }

    @Test
    void analyzeAnswerTest() throws Exception {
        //given
        String answerText = "test text";
        ApiResponseVO mockResponse = makeMockResponse();

        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody(objectMapper.writeValueAsString(mockResponse))
                .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE));

        //when
        OpenApiResponse openApiResponse = openApiService.analyzeAnswer(answerText);

        //then
        assertThat(openApiResponse.useWordVOList().size()).isEqualTo(2);
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
