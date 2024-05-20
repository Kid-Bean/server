package soongsil.kidbean.server.auth.util.kakao;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import soongsil.kidbean.server.auth.dto.response.KakaoUserResponse;

@FeignClient(name = "kakaoClient", url = "${kakao.feign.base-url}")
public interface KakaoClient {

    @GetMapping(value = "/v2/user/me", consumes = "application/json")
    KakaoUserResponse getUserData(@RequestHeader("Authorization") String accessToken);
}