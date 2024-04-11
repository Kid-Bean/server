package soongsil.kidbean.server.global.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

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
                    httpHeaders.add("Content-Type", "application/json; charset=UTF-8");
                    httpHeaders.add("Authorization", accessKey);
                })
                .build();
    }
}
