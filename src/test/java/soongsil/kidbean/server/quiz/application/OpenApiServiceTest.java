package soongsil.kidbean.server.quiz.application;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import soongsil.kidbean.server.quiz.application.vo.OpenApiResponse;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.nullable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OpenApiServiceTest {

    @Mock
    private WebClient.Builder webClientBuilder;

    @Mock
    private WebClient webClient;

    @Mock
    private WebClient.RequestBodyUriSpec requestBodyUriSpec;

    @Mock
    private WebClient.RequestBodySpec requestBodySpec;

    @Mock
    private WebClient.ResponseSpec responseSpec;

    @Mock
    private WebClient.RequestHeadersSpec requestHeadersSpec;

    @InjectMocks
    private OpenApiService openApiService;

    @BeforeEach
    public void setUp() {
        given(webClientBuilder.build()).willReturn(webClient);
        given(webClient.post()).willReturn(requestBodyUriSpec);
        given(requestBodyUriSpec.uri(nullable(String.class))).willReturn(requestBodySpec);
        given(requestBodySpec.header(anyString(), any())).willReturn(requestBodySpec);
        given(requestBodySpec.bodyValue(any())).willReturn(requestHeadersSpec);
        given(requestHeadersSpec.retrieve()).willReturn(responseSpec);

        Map<String, Object> mockResponse = new HashMap<>();
        Map<String, Object> returnObject = new HashMap<>();
        List<Map<String, Object>> sentences = List.of(
                Map.of("morp", Arrays.asList(
                        Map.of("lemma", "테스트", "type", "NNG"),
                        Map.of("lemma", "코드", "type", "NNG")
                ))
        );
        returnObject.put("sentence", sentences);
        mockResponse.put("return_object", returnObject);

        when(responseSpec.bodyToMono(Map.class)).thenReturn(Mono.just(mockResponse));
    }

    @Test
    public void testAnalyzeAnswer() {
        //when
        OpenApiResponse response = openApiService.analyzeAnswer("테스트 코드");

        //then
        assertThat(response.morphemeList().size()).isEqualTo(2);
        assertThat(response.morphemeList().get(0).morpheme()).isEqualTo("테스트");
        assertThat(response.morphemeList().get(1).morpheme()).isEqualTo("코드");
        assertThat(response.useWordList().size()).isEqualTo(2);
        assertThat(response.useWordList().stream()
                .filter(wc -> "테스트".equals(wc.word()))
                .findFirst()
                .get().count())
                .isEqualTo(1);
        assertThat(response.useWordList().stream()
                .filter(wc -> "코드".equals(wc.word()))
                .findFirst()
                .get().count())
                .isEqualTo(1);
    }
}
