package soongsil.kidbean.server.quiz.application.client;

import java.util.Map;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import soongsil.kidbean.server.quiz.application.vo.ApiResponseVO;

@FeignClient(name = "openApiClient", url = "${openApi.url}", configuration = OpenApiFeignClientConfig.class)
public interface OpenApiClient {

    @PostMapping(value = "/", consumes = "application/json")
    ApiResponseVO analyzeText(@RequestBody Map<String, Object> request);
}
