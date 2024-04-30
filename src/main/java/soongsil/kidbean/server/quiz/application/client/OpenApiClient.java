package soongsil.kidbean.server.quiz.application.client;

import java.util.Map;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import soongsil.kidbean.server.quiz.application.vo.ApiResponseVO;

//TODO 이 부분은 나중에 refactoring 할 때 추가로 WireMockServer 로 테스트 하기
@FeignClient(name = "openApiClient", url = "${openApi.url}", configuration = OpenApiFeignClientConfig.class)
public interface OpenApiClient {

    @PostMapping(value = "/", consumes = "application/json")
    ApiResponseVO analyzeText(@RequestBody Map<String, Object> request);
}
