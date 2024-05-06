package soongsil.kidbean.server.member.presentation;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import soongsil.kidbean.server.auth.dto.AuthUser;
import soongsil.kidbean.server.global.dto.ResponseTemplate;
import soongsil.kidbean.server.member.application.HomeService;
import soongsil.kidbean.server.member.dto.response.HomeResponse;

@Tag(name = "Home", description = "Home 관련 API 입니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("home")
public class HomeController {

    private final HomeService homeService;

    @GetMapping
    public ResponseEntity<ResponseTemplate<Object>> getHomeInfo(@AuthenticationPrincipal AuthUser user) {
        HomeResponse response = homeService.getHomeInfo(user.memberId());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.from(response));
    }
}
