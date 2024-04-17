package soongsil.kidbean.server.global.config;

import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@Profile("!test")
@Configuration
public class WebClientConfig {

    @Value("${openApi.accessKey}")
    private String accessKey;

    @Value("${openApi.url}")
    private String openApiURL;

    @Bean
    public WebClient webClient(WebClient.Builder builder) {
        return builder
                .baseUrl(openApiURL)
                .defaultHeaders(httpHeaders -> {
                    httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
                    httpHeaders.add(HttpHeaders.AUTHORIZATION, accessKey);
                })
                .build();
    }
}
