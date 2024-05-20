package soongsil.kidbean.server.program.presentation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import soongsil.kidbean.server.auth.dto.AuthUser;
import soongsil.kidbean.server.global.dto.ResponseTemplate;
import soongsil.kidbean.server.program.application.StarService;
import soongsil.kidbean.server.program.dto.response.StarResponse;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/program/star")
public class StarController {

    private final StarService starService;

    //즐겨 찾기 추가, 삭제
    @PostMapping("/{programId}")
    public ResponseEntity<ResponseTemplate<Object>> saveStar(
            @AuthenticationPrincipal AuthUser user,
            @PathVariable Long programId) {

        StarResponse starResponse = starService.saveStar(user.memberId(), programId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.from(starResponse));
    }
}
