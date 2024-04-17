package soongsil.kidbean.server.global.config;

import org.apache.http.HttpHeaders;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedMethods(
                        HttpMethod.POST.name(),
                        HttpMethod.GET.name(),
                        HttpMethod.PUT.name(),
                        HttpMethod.DELETE.name(),
                        HttpMethod.PATCH.name(),
                        HttpMethod.OPTIONS.name()
                )
                .allowedHeaders(HttpHeaders.AUTHORIZATION, HttpHeaders.CONTENT_TYPE)
//                JWT 구현 시 주석 해제
//                .exposedHeaders("accessToken")
                .allowCredentials(true)
                .maxAge(3600);
    }
}