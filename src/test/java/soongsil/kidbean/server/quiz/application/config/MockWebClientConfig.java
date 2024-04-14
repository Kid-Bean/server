package soongsil.kidbean.server.quiz.application.config;

import java.io.IOException;
import okhttp3.mockwebserver.MockWebServer;
import org.apache.http.HttpHeaders;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@Profile("test")
@TestConfiguration
public class MockWebClientConfig {

    @Bean
    public MockWebServer mockWebServer() throws IOException {
        MockWebServer mockWebServer = new MockWebServer();
        mockWebServer.start();
        return mockWebServer;
    }

    @Bean
    public WebClient webClient(MockWebServer mockWebServer) {
        //MockWebServer의 동적으로 생성된 URL을 사용
        String baseUrl = mockWebServer.url("/").toString();

        String accessKey = "dummy access key";
        return WebClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.AUTHORIZATION, accessKey)
                .build();
    }
}
