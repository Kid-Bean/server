package soongsil.kidbean.server.quiz.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
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
import java.util.Map;
import soongsil.kidbean.server.quiz.application.config.MockWebClientConfig;
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
        Map<String, Object> mockResponse = makeMockResponse();

        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody(objectMapper.writeValueAsString(mockResponse))
                .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE));

        //when
        OpenApiResponse openApiResponse = openApiService.analyzeAnswer(answerText);

        //then
        assertThat(openApiResponse.useWordVOList().size()).isEqualTo(2);
    }

    private Map<String, Object> makeMockResponse() {
        Map<String, Object> mockResponse = new HashMap<>();
        Map<String, Object> returnObject = new HashMap<>();
        List<Map<String, Object>> sentences = new ArrayList<>();

        // 첫 번째 문장의 형태소 분석 결과
        Map<String, Object> firstSentence = new HashMap<>();
        List<Map<String, Object>> morphemes1 = new ArrayList<>();
        Map<String, Object> morpheme1 = new HashMap<>();
        morpheme1.put("id", 1);
        morpheme1.put("text", "test");
        morpheme1.put("type", "NNG");
        morpheme1.put("lemma", "test");
        morphemes1.add(morpheme1);

        firstSentence.put("morp", morphemes1);
        sentences.add(firstSentence);

        // 두 번째 문장의 형태소 분석 결과
        Map<String, Object> secondSentence = new HashMap<>();
        List<Map<String, Object>> morphemes2 = new ArrayList<>();
        Map<String, Object> morpheme2 = new HashMap<>();
        morpheme2.put("id", 2);
        morpheme2.put("text", "text");
        morpheme2.put("type", "NNG");
        morpheme2.put("lemma", "text");
        morphemes2.add(morpheme2);

        secondSentence.put("morp", morphemes2);
        sentences.add(secondSentence);

        returnObject.put("sentence", sentences);
        mockResponse.put("return_object", returnObject);

        return mockResponse;
    }
}
