package soongsil.kidbean.server.program.presentation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import soongsil.kidbean.server.auth.dto.AuthUser;
import soongsil.kidbean.server.global.dto.ResponseTemplate;
import soongsil.kidbean.server.program.application.StarService;

import static soongsil.kidbean.server.global.dto.ResponseTemplate.EMPTY_RESPONSE;


@Slf4j
@RestController
@RequiredArgsConstructor
public class StarController {

    private final StarService starService;

    private static final int PAGE_SIZE = 4;

    //추가
    @PostMapping("program/star/{programId}")
    public ResponseEntity<ResponseTemplate<Object>> saveStar(
            @AuthenticationPrincipal AuthUser user,
            @PathVariable Long programId) {

        starService.saveStar(programId, user.memberId());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(EMPTY_RESPONSE);
    }

    //삭제
    @DeleteMapping("program/star/{programId}")
    public ResponseEntity<ResponseTemplate<Object>> deleteStar(
            @AuthenticationPrincipal AuthUser user,
            @PathVariable Long programId) {

        starService.deleteStar(programId, user.memberId());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(EMPTY_RESPONSE);
    }

    //조회
    @GetMapping("program/star/{programId}")
    public ResponseEntity<ResponseTemplate<Object>> getStars(
            @AuthenticationPrincipal AuthUser user,
            @PathVariable Long programId,
            @RequestParam(defaultValue = "0") int page) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);

        starService.getStarProgramList(programId, user.memberId(), pageable);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(EMPTY_RESPONSE);
    }
}
